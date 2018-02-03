package main.java.playback.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import main.java.playback.impl.ContentSecurityPolicyServiceImpl;
import main.java.playback.impl.CustomerInfoServiceImpl;
import main.java.playback.impl.SteeringServiceImpl;
import main.java.playback.impl.VideoMetadataServiceImpl;
import main.java.playback.models.Codec;
import main.java.playback.models.DeviceType;
import main.java.playback.models.MemberInfo;
import main.java.playback.models.Resolution;
import main.java.playback.models.Stream;

public class PlaybackManifestGenerator {
	private static final Logger LOGGER = Logger.getLogger(PlaybackManifestGenerator.class.getName());
	private static final ExecutorService EXECUTOR = Executors.newWorkStealingPool();
	
	private final ContentSecurityPolicyService _contentSercurityPolicyService;
	private final CustomerInfoService _customerInfoService;
	private final SteeringService _steeringService;
	private final VideoMetadataService _videoMetadataService;
	
	private static final long DEFAULT_TIMEOUT_IN_SEC = 5;

	public PlaybackManifestGenerator(
			ContentSecurityPolicyService contentSercurityPolicyService, 
			CustomerInfoService customerInfoService,
			SteeringService steeringService,
			VideoMetadataService videoMetadataService) {
		_contentSercurityPolicyService = contentSercurityPolicyService;
		_customerInfoService = customerInfoService;
		_steeringService = steeringService;
		_videoMetadataService = videoMetadataService;
	}
	
	/**
	 * For a given title, device type and codecs, return a collection of qualified video and audio streams
	 * 
	 * @param memberId unique identifier for the member
	 * @param title title of the asset
	 * @param videoCodec video codec the device supports
	 * @param audioCodec audio codec the device supports
	 * @param deviceType type of the device
	 * @return A map of qualified streams to the CDN urls for each stream
	 */
	public Map<Stream, List<String>> generateManifest(long memberId,
			String title, 
			Codec videoCodec, 
			Codec audioCodec,
			DeviceType deviceType) {
		Callable<MemberInfo> memberInfoTask = () -> _customerInfoService.getMemberInfo(memberId);
		Callable<Resolution> deviceResolutionTask = () -> _contentSercurityPolicyService.maxResolution(deviceType);
		try {
			MemberInfo memberInfo = EXECUTOR.submit(memberInfoTask).get(DEFAULT_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
			Resolution deviceResolution = EXECUTOR.submit(deviceResolutionTask).get(DEFAULT_TIMEOUT_IN_SEC, TimeUnit.SECONDS);
			// Return empty map if member's account is not active. 
			// If this is a real service, we can set a proper response code to indicate that member does not have access
			if (!memberInfo.isActive()) {
				return Collections.emptyMap();
			}
			
			// The max resolution a member can get is determined by member's premium status and device type
			int maxResolution = memberInfo.is4KEligible() 
					? deviceResolution.value() 
					: Math.min(Resolution.HIGH.value(), deviceResolution.value());
			
			// Get qualified streams, provide two bitrates for each audio and video resolution 
			List<Stream> qualifiedStreams = generateStreams(title, videoCodec, audioCodec, maxResolution);
			
			// For each stream, return 2 urls (if there's more than 1 url)
			return qualifiedStreams.parallelStream().collect(Collectors.toMap(
				stream -> stream,
				stream -> _steeringService.getUrls(stream.getId())
					.stream()
					.limit(2)
					.collect(Collectors.toList())
			));		
		} catch (Exception e) {
			String msg = String.format(
					"Error generating stream manifest for member %d, title %s, deviceType %s", memberId, title, deviceType);
			LOGGER.log(Level.SEVERE, msg, e);
			// If this is a real service, should return response with proper status code
			return Collections.emptyMap();
		}
	}
	
	private List<Stream> generateStreams(String title, Codec videoCodec, Codec audioCodec, int maxResolution) {
		// Get available streams for the given title, filter by codec(& resolution).
		// Group streams by resolution		
		Map<Resolution, List<Stream>> streamMap = _videoMetadataService.getStreams(title)
				.stream()
				.filter(stream -> isStreamQualified(stream, videoCodec, audioCodec, maxResolution))
				.collect(Collectors.groupingBy(Stream::getResolution));
		
		// For each resolution, keep at most 2 streams (different bitrates)
		List<Stream> qualifiedStreams = streamMap.values().stream().reduce(new ArrayList<Stream>(), 
				(list, streams) -> {
					list.addAll(streams.stream().limit(2).collect(Collectors.toList()));
					return list;
				}); 
		
		return qualifiedStreams;
	}
	
	private boolean isStreamQualified(Stream stream, Codec videoCodec, Codec audioCodec, int maxResolution) {
		// An audio stream is qualified only if codec is supported by the device 
		if (Resolution.AUDIO.equals(stream.getResolution())) {
			return audioCodec.equals(stream.getCodec());
		}
		// A video stream is qualified only if codec is supported by the device 
		// and resolution meets the requirement
		return videoCodec.equals(stream.getCodec()) 
				&& stream.getResolution().value() <= maxResolution;
	}
	
	public static void main(String[] args){

		PlaybackManifestGenerator generator = new PlaybackManifestGenerator(
				new ContentSecurityPolicyServiceImpl(),
				new CustomerInfoServiceImpl(),
				new SteeringServiceImpl(),
				new VideoMetadataServiceImpl());
		
		Map<Stream, List<String>> streams = generator
				.generateManifest(123, "Stranger Things", Codec.VIDEO_H264, Codec.AUDIO_AAC, DeviceType.CHROME_CAST);
		
		streams.entrySet().stream().forEach(entry -> {
			System.out.println(String.format("stream id: %d, urls: %s, %s", 
					entry.getKey().getId(), 
					entry.getValue().get(0),
					entry.getValue().get(1)));
		});
	}
}
