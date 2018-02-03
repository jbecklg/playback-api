package main.java.playback.models;

public class MemberInfo {
	private boolean _isActive;
	private boolean _is4KEligible;
	
	public MemberInfo(boolean isActive, boolean is4KEligible) {
		_isActive = isActive;
		_is4KEligible = is4KEligible;
	}
	
	public boolean isActive() {
		return _isActive;
	}
	
	public void setActive(boolean isActive) {
		_isActive = isActive;
	}
	
	public boolean is4KEligible() {
		return _is4KEligible;
	}
	
	public void set4KEligible(boolean is4KEligible) {
		_is4KEligible = is4KEligible;
	}
}
