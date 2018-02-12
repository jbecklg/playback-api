package main.java.playback.mock;

import main.java.content.VideoStream;
import main.java.playback.api.ContentSecurityPolicyService;
import main.java.session.Device;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MockContentSecurityPolicyServiceImpl implements ContentSecurityPolicyService {
    @Override
    public CompletableFuture<VideoStream.Resolution> getMaxResolution(Device.Type deviceType, Executor executor) {
        return CompletableFuture.supplyAsync(() -> getResolutionByDevice(deviceType), executor);
    }

    private VideoStream.Resolution getResolutionByDevice (Device.Type deviceType) {
        try {
            // Fake service latency.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        VideoStream.Resolution resolution;
        switch(deviceType) {
            case MOBILE:
            case TABLET:
                resolution = VideoStream.Resolution.R720p;
                break;
            case TV_FHD:
                resolution = VideoStream.Resolution.R1080p;
                break;
            case TV_UHD:
                resolution = VideoStream.Resolution.R2160p;
                break;
            default:
                resolution = VideoStream.Resolution.R720p;
        }
        return resolution;
    }
}
