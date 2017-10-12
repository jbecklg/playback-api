package playback.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Map;

public class PlaybackManifest {

    private Map<Codec, List<PlaybackStream>> streams;

    public PlaybackManifest() {}

    public PlaybackManifest(Map<Codec, List<PlaybackStream>> streams) {
        this.streams = streams;
    }

    @JsonValue
    public Map<Codec, List<PlaybackStream>> getStreams() {
        return streams;
    }
}
