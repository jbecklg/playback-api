package main.java.playback.mock;

import main.java.content.CdnUrl;
import main.java.content.Stream;
import main.java.playback.api.SteeringService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MockSteeringServiceImpl implements SteeringService {
    private static Map<String, CdnUrl> mockStreamIdToCdnUrlMap = new HashMap<>();

    static {
        try {
            mockStreamIdToCdnUrlMap.put("planet_earth",
                    new CdnUrl(new URL("https://cdn/planet_earth_1"),
                            new URL("https://cdn/planet_earth_2")));
            mockStreamIdToCdnUrlMap.put("narcos",
                    new CdnUrl(new URL("https://cdn/narcos_1"),
                            new URL("https://cdn/narcos_2")));
            mockStreamIdToCdnUrlMap.put("zootopia",
                    new CdnUrl(new URL("https://cdn/zootopia_1"),
                            new URL("https://cdn/zootopia_2")));
        } catch (MalformedURLException e) {}
    }

    @Override
    public CompletableFuture<CdnUrl> getCdnUrl(Stream stream, Executor executor) {
        return CompletableFuture.supplyAsync(() -> getCdnUrlInternal(stream), executor);
    }

    private CdnUrl getCdnUrlInternal(Stream stream) {
        try {
            // Fake service latency.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return mockStreamIdToCdnUrlMap.get(stream.getId());
    }
}
