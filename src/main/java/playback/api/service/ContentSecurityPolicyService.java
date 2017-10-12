package playback.api.service;

import playback.api.model.Codec;
import playback.api.model.DeviceType;

public interface ContentSecurityPolicyService {

    public Codec getMaxSupportedCodec(DeviceType deviceType);

}
