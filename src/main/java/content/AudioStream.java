package main.java.content;

public class AudioStream extends Stream {
    public AudioStream(String id, AudioCodec codec, AudioBitrate bitrate,
                       Language language) {
        super(id, codec, bitrate, language);
    }

    @Override
    public String toString() {
        return "AudioStream{" +
                "id='" + id + '\'' +
                ", codec=" + codec +
                ", bitrate=" + bitrate +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof AudioStream) {
            AudioStream that = (AudioStream) obj;
            return id.equals(that.id) && codec.equals(that.codec) && bitrate
                    == that.bitrate && language == that.language;
        }
        return false;
    }
}
