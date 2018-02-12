package main.java.playback.api;

import main.java.asset.Asset;
import main.java.content.*;
import main.java.content.VideoStream.Resolution;
import main.java.customer.CustomerInfo;
import main.java.customer.CustomerInfo.Plan;
import main.java.customer.CustomerInfo.Status;
import main.java.playback.model.PlaybackError;
import main.java.playback.model.PlaybackManifest;
import main.java.playback.model.PlaybackManifestRequest;
import main.java.playback.model.PlaybackManifestResponse;
import main.java.session.Device;
import main.java.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class PlaybackManifestGenerator {
    private static final long DEFAULT_TIMEOUT_SEC = 5;
    private CustomerInfoService customerInfoService;
    private ContentSecurityPolicyService contentSecurityPolicyService;
    private VideoMetadataService videoMetadataService;
    private SteeringService steeringService;
    private Executor executor;

    public PlaybackManifestGenerator(CustomerInfoService customerInfoService,
                                     ContentSecurityPolicyService contentSecurityPolicyService,
                                     VideoMetadataService videoMetadataService,
                                     SteeringService steeringService,
                                     Executor executor) {
        this.customerInfoService = customerInfoService;
        this.contentSecurityPolicyService = contentSecurityPolicyService;
        this.videoMetadataService = videoMetadataService;
        this.steeringService = steeringService;
        this.executor = executor;
    }

    /**
     * Get the playback manifests for a user session and asset.
     *
     * @param request PlaybackManifestRequest.
     * @return PlaybackManifestResponse.
     * @throws InterruptedException
     */
    public PlaybackManifestResponse getManifests(
            PlaybackManifestRequest request) throws InterruptedException {
        // Async calls to:
        // 1. CustomerInfoService.
        Session session = request.getSession();
        CompletableFuture<CustomerInfo> customerInfoFuture =
                customerInfoService.getCustomerInfo(session, executor);
        // 2. ContentSecurityPolicyService.
        Device device = session.getDevice();
        CompletableFuture<Resolution> maxDeviceResolutionFuture =
                contentSecurityPolicyService.getMaxResolution(device.getType(),
                        executor);
        // 3. SteeringService.
        Map<Stream, CompletableFuture<CdnUrl>> streamToAsyncUrlMap =
                getStreamToUrlMap(device, request.getAsset());

        // Get customer information.
        CustomerInfo customerInfo = null;
        try {
            customerInfo = customerInfoFuture.get(DEFAULT_TIMEOUT_SEC,
                    TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException e) {
            // Assumption: A majority of users hitting this are active
            // customers.
            customerInfo = new CustomerInfo(Status.ACTIVE, Plan.STANDARD);
        }

        if (customerInfo.getStatus() == Status.INACTIVE) {
            // Notify device with the error.
            return new PlaybackManifestResponse(PlaybackError
                    .CUSTOMER_INACTIVE);
        }

        Resolution maxResolution;
        try {
            maxResolution = maxDeviceResolutionFuture.get
                    (DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);
            if (maxResolution == Resolution.R2160p && customerInfo.getPlan() !=
                    Plan.PREMIUM) {
                maxResolution = Resolution.R1080p;
            }
        } catch (TimeoutException | ExecutionException e) {
            // Default to the least resolution as devices might not support
            // higher resolutions.
            maxResolution = Resolution.R720p;
        }

        // Filter out streams based on video resolution; obtain CDN URLs.
        List<PlaybackManifest> playbackManifests = new ArrayList<>();
        for (Map.Entry<Stream, CompletableFuture<CdnUrl>> streamToAsyncUrl :
                streamToAsyncUrlMap.entrySet()) {
            Stream stream = streamToAsyncUrl.getKey();
            if (stream instanceof VideoStream) {
                Resolution resolution = ((VideoStream) stream).getResolution();
                if (maxResolution.compareTo(resolution) < 0) {
                    continue;
                }
            }

            try {
                CdnUrl cdnUrl = streamToAsyncUrl.getValue().get
                        (DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);
                playbackManifests.add(new PlaybackManifest(stream, cdnUrl));
            } catch (TimeoutException | ExecutionException e) {
                // Assumption: Critical failure as the customer cannot find
                // the source of either audio/video streams.
                return new PlaybackManifestResponse(PlaybackError
                        .DEPENDENCY_FAILURE);
            }
        }

        return new PlaybackManifestResponse(playbackManifests);
    }

    /**
     * Derive a map of stream to CDN URLs for a given asset and device.
     *
     * @param device Device of the customer.
     * @param asset Asset.
     * @return A map of Stream to CdnUrl futures.
     */
    private Map<Stream, CompletableFuture<CdnUrl>> getStreamToUrlMap(
            Device device, Asset asset) {
        Map<Stream, CompletableFuture<CdnUrl>> streamToAsyncUrlMap = new
                HashMap<>();
        AudioCodec supportedAudioCodec = device.getAudioCodec();
        VideoCodec supportedVideoCodec = device.getVideoCodec();
        List<Stream> assetStreams = videoMetadataService.getStreams(asset);

        for (Stream stream : assetStreams) {
            // Skip the streams for unsupported codecs.
            Codec codec = stream.getCodec();
            if (codec != supportedVideoCodec && codec != supportedAudioCodec) {
                continue;
            }

            CompletableFuture<CdnUrl> urlsFuture = steeringService.getCdnUrl(
                    stream, executor);
            streamToAsyncUrlMap.put(stream, urlsFuture);
        }
        return streamToAsyncUrlMap;
    }
}
