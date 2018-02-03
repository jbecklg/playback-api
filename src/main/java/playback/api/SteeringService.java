package main.java.playback.api;

import java.util.List;

/**
 * This service provides URLs for streams
 */
public interface SteeringService {

	/**
	 * Get a list of URLs where a stream is located
	 * @param id Unique identifier for a stream
	 * @return URLs where the given stream can be found
	 */
	List<String> getUrls (long id);
}
