package playback.api.impl;

import org.springframework.stereotype.Service;
import playback.api.model.Codec;
import playback.api.model.DeviceType;
import playback.api.service.ContentSecurityPolicyService;

@Service
public class ContentSecurityPolicyServiceImpl implements ContentSecurityPolicyService {

    public Codec getMaxSupportedCodec(DeviceType deviceType) {
        if (DeviceType.APPLE_TVOS.equals(deviceType)) {
            return Codec.VIDEO_H264_2160P;
        } else if(DeviceType.SONY_PS3.equals(deviceType)) {
            return Codec.VIDEO_H264_1080P;
        }
        return Codec.VIDEO_H264_720P;
    }
}
