package main.java.playback.model;

import main.java.common.model.ApiResponse;

import java.util.List;

/**
 * A server response of the playback manifest.
 */
public class PlaybackManifestResponse extends ApiResponse {
    private List<PlaybackManifest> manifestList;

    public PlaybackManifestResponse(List<PlaybackManifest> manifestList) {
        this.manifestList = manifestList;
    }

    public PlaybackManifestResponse(PlaybackError error) {
        this.error = error;
    }

    /**
     * Get the list of playback manifests.
     */
    public List<PlaybackManifest> getManifestList() {
        return manifestList;
    }

    @Override
    public String toString() {
        return "PlaybackManifestResponse{\n" +
                "  manifestList=" + manifestList +
                ",\n  error=" + error +
                "\n}";
    }
}
