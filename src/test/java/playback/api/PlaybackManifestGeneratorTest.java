package test.java.playback.api;

import static org.junit.Assert.*;

import main.java.asset.Asset;
import main.java.content.*;
import main.java.playback.api.PlaybackManifestGenerator;
import main.java.playback.mock.MockContentSecurityPolicyServiceImpl;
import main.java.playback.mock.MockCustomerInfoServiceImpl;
import main.java.playback.mock.MockSteeringServiceImpl;
import main.java.playback.mock.MockVideoMetadataServiceImpl;
import main.java.playback.model.PlaybackError;
import main.java.playback.model.PlaybackManifest;
import main.java.playback.model.PlaybackManifestRequest;
import main.java.playback.model.PlaybackManifestResponse;
import main.java.session.Device;
import main.java.session.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PlaybackManifestGeneratorTest {
    PlaybackManifestGenerator playbackManifestGenerator;

    private static final String ACTIVE_PREMIUM = "session1_active_premium";
    private static final String ACTIVE_STANDARD = "session2_active_standard";
    private static final String INACTIVE = "session3_inactive";
    private static final Device MOBILE = new Device
            (Device.Type.MOBILE, AudioCodec.DOLBY, VideoCodec.H264);
    private static final Device TV_UHD = new Device
            (Device.Type.TV_UHD, AudioCodec.AAC, VideoCodec.HEVC);
    private static final Asset ASSET = new Asset
            ("planet_earth", "Planet Earth");
    private static final Language LANGUAGE = Language.ENGLISH;

    @Before
    public void setUp() throws Exception {
        playbackManifestGenerator = new PlaybackManifestGenerator(
                new MockCustomerInfoServiceImpl(),
                new MockContentSecurityPolicyServiceImpl(),
                new MockVideoMetadataServiceImpl(),
                new MockSteeringServiceImpl(),
                Executors.newFixedThreadPool(50));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInactiveUser_error() throws Exception {
        PlaybackManifestRequest request = new PlaybackManifestRequest(new
                Session(INACTIVE, MOBILE), ASSET);
        PlaybackManifestResponse response = playbackManifestGenerator
                .getManifests(request);

        assertNull(response.getManifestList());
        assertEquals(response.getError(), PlaybackError.CUSTOMER_INACTIVE);
    }

    @Test
    public void testActivePremiumUser_has4K() throws Exception {
        Stream expected = new VideoStream(ASSET.getId(), TV_UHD.getVideoCodec(),
                VideoBitrate.HIGH_FRAME, VideoStream.Resolution.R2160p,
                LANGUAGE);

        PlaybackManifestRequest request = new PlaybackManifestRequest(new
                Session(ACTIVE_PREMIUM, TV_UHD), ASSET);
        PlaybackManifestResponse response = playbackManifestGenerator
                .getManifests(request);

        assertFalse(response.hasError());
        assertTrue(response.getManifestList().stream().map
                (PlaybackManifest::getStream).collect(Collectors.toList())
                .contains(expected));
    }

    @Test
    public void testActiveStandardUser_hasNo4K() throws Exception {
        Stream notExpected = new VideoStream(ASSET.getId(), TV_UHD
                .getVideoCodec(),
                VideoBitrate.HIGH_FRAME, VideoStream.Resolution.R2160p,
                LANGUAGE);

        PlaybackManifestRequest request = new PlaybackManifestRequest(new
                Session(ACTIVE_STANDARD, TV_UHD), ASSET);
        PlaybackManifestResponse response = playbackManifestGenerator
                .getManifests(request);

        assertFalse(response.hasError());
        assertFalse(response.getManifestList().stream().map
                (PlaybackManifest::getStream).collect(Collectors.toList())
                .contains(notExpected));
    }
}