package main.java.session;

public class Session {

    private String sessionId;
    private Device device;

    public Session(String sessionId, Device device) {
        this.sessionId = sessionId;
        this.device = device;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Device getDevice() {
        return device;
    }
}
