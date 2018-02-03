package main.java.playback.models;

public enum Resolution {
	//720P video resolution
	LOW(720),
	
	//1080P video resolution
	MEDIUM(1080),
	
	//2160P/4K video resolution
	HIGH(2160),
	
	//represents a video stream
	AUDIO(-1);

	private final int _resolution;
	
	private Resolution(int resolution) {
		_resolution = resolution;
	}

	/**
	 * @return Numeric representation of the resolution
	 */
	public int value() {
		return _resolution;
	}
}
