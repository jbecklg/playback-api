package main.java.session;

import main.java.content.AudioCodec;
import main.java.content.VideoCodec;

public class Device {
    public enum Type {
        MOBILE, TABLET, TV_FHD, TV_UHD
    }

    private Type type;
    private AudioCodec audioCodec;
    private VideoCodec videoCodec;

    public Device(Type type, AudioCodec audioCodec, VideoCodec videoCodec) {
        this.type = type;
        this.audioCodec = audioCodec;
        this.videoCodec = videoCodec;
    }

    public Type getType() {
        return type;
    }

    public AudioCodec getAudioCodec() {
        return audioCodec;
    }

    public VideoCodec getVideoCodec() {
        return videoCodec;
    }
}
