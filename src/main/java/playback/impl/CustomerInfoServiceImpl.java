package main.java.playback.impl;

import main.java.playback.api.CustomerInfoService;
import main.java.playback.models.MemberInfo;


/**
 * This service provides member's account information.
 */
public class CustomerInfoServiceImpl implements CustomerInfoService {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MemberInfo getMemberInfo(long memberId) {
		// Mock return data
		return new MemberInfo(true, true); 
	}
}
