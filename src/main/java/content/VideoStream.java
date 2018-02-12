package main.java.content;

public class VideoStream extends Stream {
    public enum Resolution {
        R720p,
        R1080p,
        R2160p
    }
    Resolution resolution;

    public VideoStream(String id, VideoCodec codec, VideoBitrate bitrate,
                       Resolution resolution, Language language) {
        super(id, codec, bitrate, language);
        this.resolution = resolution;
    }

    public Resolution getResolution() {
        return resolution;
    }

    @Override
    public String toString() {
        return "VideoStream{" +
                "resolution=" + resolution +
                ", id='" + id + '\'' +
                ", codec=" + codec +
                ", bitrate=" + bitrate +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof VideoStream) {
            VideoStream that = (VideoStream) obj;
            return resolution == that.resolution && id.equals(that.id) &&
                    codec.equals(that.codec) && bitrate == that.bitrate &&
                    language == that.language;
        }
        return false;
    }
}
