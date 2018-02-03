package main.java.playback.impl;

import java.util.ArrayList;
import java.util.List;

import main.java.playback.api.SteeringService;

/**
 * This service provides URLs for streams.
 */
public class SteeringServiceImpl implements SteeringService {
	// Static mock data
	private static final String MOCK_URL_ONE = "http://playback.api.stream-url-1";
	private static final String MOCK_URL_TWO = "http://playback.api.stream-url-2";
	private static final String MOCK_URL_THREE = "http://playback.api.stream-url-3";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUrls (long id) {
		// Mock return data
		List<String> urls = new ArrayList<String>();
		urls.add(MOCK_URL_ONE);
		urls.add(MOCK_URL_TWO);
		urls.add(MOCK_URL_THREE);
		return urls;
	}
}
