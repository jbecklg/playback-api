package playback.api;

import org.springframework.stereotype.Service;
import playback.api.model.*;
import playback.api.service.ContentSecurityPolicyService;
import playback.api.service.CustomerInfoService;
import playback.api.service.SteeringService;
import playback.api.service.VideoMetadataService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlaybackManifestGenerator {

    private final CustomerInfoService customerInfoService;
    private final ContentSecurityPolicyService contentSecurityPolicyService;
    private final SteeringService steeringService;
    private final VideoMetadataService videoMetadataService;

    public PlaybackManifestGenerator(CustomerInfoService customerInfoService,
                                     ContentSecurityPolicyService contentSecurityPolicyService,
                                     SteeringService steeringService,
                                     VideoMetadataService videoMetadataService) {
        this.customerInfoService = customerInfoService;
        this.contentSecurityPolicyService = contentSecurityPolicyService;
        this.steeringService = steeringService;
        this.videoMetadataService = videoMetadataService;
    }

    public PlaybackManifest generatePlaybackManifest(UUID titleId, DeviceType deviceType, UUID userId) throws InactiveUserException {

        Codec maxVideoCodec = contentSecurityPolicyService.getMaxSupportedCodec(deviceType);
        final CustomerInfo customerInfo = customerInfoService.getCustomerInfo(userId);

        if (!customerInfo.isActive()) {
            throw new InactiveUserException("Inactive User");
        }

        if (Codec.VIDEO_H264_2160P.equals(maxVideoCodec) && !CustomerTier.PREMIUM.equals(customerInfo.getCustomerTier())) {
            maxVideoCodec = Codec.VIDEO_H264_1080P;
        }

        final Map<Codec, List<PlaybackStream>> allStreams = videoMetadataService.getStreams(titleId, maxVideoCodec);

        for (List<PlaybackStream> streams : allStreams.values()) {
            // Assumption is there are two bitrates per codec, prune if not the case

            for (PlaybackStream stream : streams) {
                List<URI> urls = steeringService.getStreamURLs(stream.getId())
                        .stream()
                        .limit(3)
                        .collect(Collectors.toList());
                stream.setUrls(urls);
            }
        }

        return new PlaybackManifest(allStreams);

    }
}
