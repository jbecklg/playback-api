package main.java.playback.model;

import main.java.common.model.Error;

public enum PlaybackError implements Error {
    CUSTOMER_INACTIVE {
        @Override
        public String getDescription() {
            return "Not an active member of Netflix.";
        }
    },
    DEPENDENCY_FAILURE {
        @Override
        public String getDescription() {
            return "One or more Netflix services failed.";
        }
    };
}
