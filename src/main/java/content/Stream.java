package main.java.content;

public abstract class Stream {
    String id;
    Codec codec;
    Bitrate bitrate;
    Language language;

    public Stream(String id, Codec codec, Bitrate bitrate, Language language) {
        this.id = id;
        this.codec = codec;
        this.bitrate = bitrate;
        this.language = language;
    }

    public Codec getCodec() {
        return codec;
    }

    public Bitrate getBitrate() {
        return bitrate;
    }

    public String getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }
}
