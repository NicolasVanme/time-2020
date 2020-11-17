package org.timetracking.event;

/**
 * Runtime exception to manage error of time tracking event communication
 */
public class TimeTrackingEventException extends RuntimeException {

    public TimeTrackingEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeTrackingEventException(String message) {
        super(message);
    }
}
