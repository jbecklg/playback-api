package main.java.common.model;

import main.java.session.Session;

/**
 * Generic Netflix API request.
 */
public abstract class ApiRequest {
    private Session session;

    protected ApiRequest(Session session) {
        this.session = session;
    }

    /**
     * Gets the user session object.
     */
    public Session getSession() {
        return session;
    }
}
