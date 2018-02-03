package main.java.playback.impl;

import main.java.playback.api.ContentSecurityPolicyService;
import main.java.playback.models.DeviceType;
import main.java.playback.models.Resolution;

/**
 * This service determines max resolution for each device type.
 */
public class ContentSecurityPolicyServiceImpl implements ContentSecurityPolicyService {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Resolution maxResolution(DeviceType type) {
		// Mock return data
		if (DeviceType.XBOX_ONE.equals(type) || DeviceType.CHROME_CAST.equals(type)) {
			return Resolution.HIGH;
		}
		return Resolution.MEDIUM;
	}
}
