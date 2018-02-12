package main.java.playback.model;

import main.java.content.CdnUrl;
import main.java.content.Stream;

public class PlaybackManifest {
    private Stream stream;
    private CdnUrl url;

    public PlaybackManifest(Stream stream, CdnUrl url) {
        this.stream = stream;
        this.url = url;
    }

    public Stream getStream() {
        return stream;
    }

    public CdnUrl getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "\n\tPlaybackManifest{" +
                "stream=" + stream +
                ", url=" + url +
                "}";
    }
}
