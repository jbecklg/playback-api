package playback.api.impl;

import org.springframework.stereotype.Service;
import playback.api.model.Codec;
import playback.api.model.PlaybackStream;
import playback.api.service.VideoMetadataService;

import java.util.*;

@Service
public class VideoMetadataServiceImpl implements VideoMetadataService {

    public Map<Codec, List<PlaybackStream>> getStreams(UUID titleId, Codec maxVideoCodec) {

        Map<Codec, List<PlaybackStream>> streams = new HashMap<>();

        streams.put(Codec.AUDIO_AAC, Arrays.asList(
           new PlaybackStream(UUID.randomUUID(), 64),
           new PlaybackStream(UUID.randomUUID(), 128))
        );

        if(maxVideoCodec.ordinal() >= Codec.VIDEO_H264_720P.ordinal()) {
            streams.put(Codec.VIDEO_H264_720P, Arrays.asList(
                    new PlaybackStream(UUID.randomUUID(), 1000),
                    new PlaybackStream(UUID.randomUUID(), 1200)
            ));

            if(maxVideoCodec.ordinal() >= Codec.VIDEO_H264_1080P.ordinal()) {
                streams.put(Codec.VIDEO_H264_1080P, Arrays.asList(
                        new PlaybackStream(UUID.randomUUID(), 2000),
                        new PlaybackStream(UUID.randomUUID(), 4000)
                ));

                if(maxVideoCodec.ordinal() >= Codec.VIDEO_H264_2160P.ordinal()) {
                    streams.put(Codec.VIDEO_H264_2160P, Arrays.asList(
                            new PlaybackStream(UUID.randomUUID(), 4000),
                            new PlaybackStream(UUID.randomUUID(), 8000)
                    ));
                }
            }
        }

        return streams;
    }
}
