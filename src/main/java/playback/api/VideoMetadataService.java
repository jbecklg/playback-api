package main.java.playback.api;

import main.java.playback.models.Stream;
import java.util.List;


/**
 * This service is used to fetch meta data for video streams
 */
public interface VideoMetadataService {
	/**
	 * Get all video streams for the given title
	 * @param title title of the asset
	 * @return A list of streams that are available for the given title
	 */
	List<Stream> getStreams (String title); 
}
