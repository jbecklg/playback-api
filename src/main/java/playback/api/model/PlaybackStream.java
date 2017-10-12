package playback.api.model;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public class PlaybackStream {

    private UUID id;
    private long bitrateKbps;
    private List<URI> urls;

    public PlaybackStream() {}

    public PlaybackStream(UUID id, long bitrateKbps) {
        this.id = id;
        this.bitrateKbps = bitrateKbps;
    }

    public UUID getId() {
        return id;
    }

    public long getBitrateKbps() {
        return bitrateKbps;
    }

    public void setUrls(List<URI> urls) {
        this.urls = urls;
    }

    public List<URI> getUrls() {
        return urls;
    }
}
