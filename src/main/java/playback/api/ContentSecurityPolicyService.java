package main.java.playback.api;

import main.java.content.VideoStream.Resolution;
import main.java.session.Device;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * ContentSecurityPolicyService (external service).
 */
public interface ContentSecurityPolicyService {
    /**
     * Get the maximum resolution of a device.
     *
     * @param deviceType Type of device.
     * @param executor Executor to run the network call.
     * @return A Resolution future.
     */
    CompletableFuture<Resolution> getMaxResolution(Device.Type deviceType,
                                                   Executor executor);
}
