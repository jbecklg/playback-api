package main.java.playback.mock;

import main.java.asset.Asset;
import main.java.content.*;
import main.java.content.VideoStream.Resolution;
import main.java.playback.api.VideoMetadataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MockVideoMetadataServiceImpl implements VideoMetadataService {
    private static List<Stream> streams = new ArrayList<>();
    static {
        for (String streamId : Arrays.asList("planet_earth", "narcos", "zootopia")) {
            for (Language language : Language.values()) {
                for (VideoBitrate bitrate : VideoBitrate.values()) {
                    for (Resolution resolution : Resolution.values()) {
                        for (VideoCodec codec : VideoCodec.values()) {
                            streams.add(new VideoStream(streamId, codec,
                                    bitrate, resolution, language));
                        }
                    }
                }
                for (AudioBitrate bitrate : AudioBitrate.values()) {
                    for (AudioCodec codec : AudioCodec.values()) {
                        streams.add(new AudioStream(streamId, codec, bitrate,
                                language));
                    }
                }
            }
        }
    }

    @Override
    public List<Stream> getStreams(Asset asset) {
        return streams.stream().filter(stream -> stream.getId().equals(asset.getId())).collect(Collectors.toList());
    }
}
