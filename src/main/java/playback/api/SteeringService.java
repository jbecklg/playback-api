package main.java.playback.api;

import main.java.content.CdnUrl;
import main.java.content.Stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * SteeringService (external service).
 */
public interface SteeringService {
    /**
     * Get the CDN URLs for a given stream.
     *
     * @param stream A (audio/video) stream.
     * @param executor Executor to run the network call.
     * @return CDN URLs (primary, secondary) future.
     */
    CompletableFuture<CdnUrl> getCdnUrl(Stream stream, Executor executor);
}
