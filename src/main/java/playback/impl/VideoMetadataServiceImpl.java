package main.java.playback.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.playback.api.VideoMetadataService;
import main.java.playback.models.Codec;
import main.java.playback.models.Resolution;
import main.java.playback.models.Stream;

/**
 * This service is used to fetch meta data for video and audio streams.
 */
public class VideoMetadataServiceImpl implements VideoMetadataService {
	
	/*
	 * {@inheritDoc}
	 */
	@Override
	public List<Stream> getStreams (String title) {
		// Mock return data
		List<Stream> streams = new ArrayList<Stream>();
		streams.add(
				new Stream(1L, 1350, Codec.VIDEO_H264, Resolution.LOW)
		);
		streams.add(
				new Stream(2L, 1360, Codec.VIDEO_H264, Resolution.LOW)
		);
		streams.add(
				new Stream(3L, 1370, Codec.VIDEO_H264, Resolution.LOW)
		);
		streams.add(
				new Stream(4L, 2350, Codec.VIDEO_H264, Resolution.MEDIUM)
		);
		streams.add(
				new Stream(5L, 2360, Codec.VIDEO_H264, Resolution.MEDIUM)
		);
		streams.add(
				new Stream(6L, 3350, Codec.VIDEO_H264, Resolution.HIGH)
		);
		streams.add(
				new Stream(7L, 3390, Codec.VIDEO_VP9, Resolution.HIGH)
		);
		streams.add(
				new Stream(8L, 2000, Codec.AUDIO_AAC, Resolution.AUDIO)
		);
		streams.add(
				new Stream(9L, 3000, Codec.AUDIO_AAC, Resolution.AUDIO)
		);
		return streams;
	}
}
