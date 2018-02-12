package main.java.common.model;

import main.java.common.model.Error;

/**
 * Generic Netflix API response.
 */
public abstract class ApiResponse {
    protected Error error;

    /**
     * Get the error code.
     */
    public Error getError() {
        return error;
    }

    /**
     * Flag indicating if the response has an error.
     */
    public boolean hasError() {
        return error != null;
    }
}
