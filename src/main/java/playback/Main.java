package main.java.playback;

import main.java.asset.Asset;
import main.java.content.AudioCodec;
import main.java.content.VideoCodec;
import main.java.playback.api.PlaybackManifestGenerator;
import main.java.playback.mock.MockContentSecurityPolicyServiceImpl;
import main.java.playback.mock.MockCustomerInfoServiceImpl;
import main.java.playback.mock.MockSteeringServiceImpl;
import main.java.playback.mock.MockVideoMetadataServiceImpl;
import main.java.playback.model.PlaybackManifestRequest;
import main.java.playback.model.PlaybackManifestResponse;
import main.java.session.Device;
import main.java.session.Session;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        PlaybackManifestGenerator playbackManifestGenerator =
                new PlaybackManifestGenerator(new MockCustomerInfoServiceImpl(),
                        new MockContentSecurityPolicyServiceImpl(),
                        new MockVideoMetadataServiceImpl(),
                        new MockSteeringServiceImpl(),
                        Executors.newFixedThreadPool(50));

        String session = "session1_active_premium";
        Device device = new Device(Device.Type.TV_UHD, AudioCodec.DOLBY,
                VideoCodec.H264);
        Asset asset = new Asset("zootopia", "Zootopia");

        long startTime = System.currentTimeMillis();
        PlaybackManifestRequest request = new PlaybackManifestRequest(
                new Session(session, device), asset);
        PlaybackManifestResponse response = playbackManifestGenerator
                .getManifests(request);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("Duration (millis): " + duration);
        System.out.println("Response: " + response);

        System.exit(0);
    }
}
