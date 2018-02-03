package main.java.playback.api;

import main.java.playback.models.MemberInfo;

/**
 * This service provides member's account information.
 */
public interface CustomerInfoService {

	/**
	 * Get member's account information
	 * 
	 * @param memberId Unique identifier for a member
	 * @return Member account information
	 */
	MemberInfo getMemberInfo(long memberId);
}
