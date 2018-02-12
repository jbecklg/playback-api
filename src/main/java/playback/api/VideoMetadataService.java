package main.java.playback.api;

import main.java.asset.Asset;
import main.java.content.Stream;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * VideoMetadataService (local client).
 */
public interface VideoMetadataService {
    /**
     * Get a list of streams for a given asset.
     *
     * @param asset
     * @return List of streams with different codecs, bitrates, etc.
     */
    List<Stream> getStreams(Asset asset);
}
