package main.java.playback.models;

public class Stream {
	private long _id;
	private int _bitrate;
	private Codec _codec; 
	private Resolution _resolution;
	
	public Stream(long id, int bitrate, Codec codec, Resolution resolution) {
		_id = id;
		_bitrate = bitrate;
		_codec = codec;
		_resolution = resolution;
	}
	
	public long getId() {
		return _id;
	}
	
	public void setId(long id) {
		_id = id;
	}
	
	public int getBitrate() {
		return _bitrate;
	}
	
	public void setBitrate(int bitrate) {
		_bitrate = bitrate;
	}
	
	public Codec getCodec() {
		return _codec;
	}
	
	public void setCodec(Codec codec) {
		_codec = codec;
	}

	public Resolution getResolution() {
		return _resolution;
	}
	
	public void setResolution(Resolution resolution) {
		_resolution = resolution;
	}
}
