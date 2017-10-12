package playback.api.service;

import playback.api.model.Codec;
import playback.api.model.PlaybackStream;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VideoMetadataService {

    public Map<Codec, List<PlaybackStream>> getStreams(UUID titleId, Codec maxVideoCodec);

}
