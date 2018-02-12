package main.java.content;

import java.net.URL;

public class CdnUrl {
    private URL primary;
    private URL fallback;

    public CdnUrl(URL primary, URL fallback) {
        this.primary = primary;
        this.fallback = fallback;
    }

    public URL getPrimary() {
        return primary;
    }

    public URL getFallback() {
        return fallback;
    }

    @Override
    public String toString() {
        return "CdnUrl{" +
                "primary=" + primary +
                ", fallback=" + fallback +
                '}';
    }
}
