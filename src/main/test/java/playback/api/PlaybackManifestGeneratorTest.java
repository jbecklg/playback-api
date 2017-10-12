package playback.api;

import org.junit.Test;
import org.mockito.Mockito;
import playback.api.impl.ContentSecurityPolicyServiceImpl;
import playback.api.impl.SteeringServiceImpl;
import playback.api.impl.VideoMetadataServiceImpl;
import playback.api.model.CustomerInfo;
import playback.api.model.CustomerTier;
import playback.api.model.DeviceType;
import playback.api.model.PlaybackManifest;
import playback.api.service.CustomerInfoService;

import java.util.UUID;

import static org.junit.Assert.*;

public class PlaybackManifestGeneratorTest {

    CustomerInfoService customerInfoService = Mockito.mock(CustomerInfoService.class);

    PlaybackManifestGenerator playbackManifestGenerator = new PlaybackManifestGenerator(
            customerInfoService,
            new ContentSecurityPolicyServiceImpl(),
            new SteeringServiceImpl(),
            new VideoMetadataServiceImpl()
    );

    @Test
    public void generatePlaybackManifest() throws Exception {
        UUID titleId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Mockito.when(customerInfoService.getCustomerInfo(userId))
                .thenReturn(new CustomerInfo(true, CustomerTier.PREMIUM));
        PlaybackManifest manifest
                = playbackManifestGenerator.generatePlaybackManifest(titleId, DeviceType.APPLE_TVOS, userId);
        assertNotNull(manifest);
    }

}