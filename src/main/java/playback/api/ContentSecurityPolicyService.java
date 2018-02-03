package main.java.playback.api;

import main.java.playback.models.DeviceType;
import main.java.playback.models.Resolution;

/**
 * This service determines max resolution for each device type.
 */
public interface ContentSecurityPolicyService {
	
	/**
	 * Get the max resolution of the given device type.
	 * 
	 * @param type The type of the device
	 * @return Max resolution the device supports
	 */
	Resolution maxResolution(DeviceType type);
}
