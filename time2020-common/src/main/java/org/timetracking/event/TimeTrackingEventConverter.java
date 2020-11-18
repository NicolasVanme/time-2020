package org.timetracking.event;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;

/**
 * Used to :
 * - Convert time tracking event to string
 * - Convert string to time tracking event
 */
public class TimeTrackingEventConverter {

    public String convertToString(TimeTrackingEvent event) {
        return  format("%d::%d::%s::%d",
                event.getEmployeeId(),
                event.getTaskId(),
                event.getType().name(),
                toSecond(event.getEventDateTime()));
    }

    public TimeTrackingEvent convertFromString(String message) {
        String[] messageParts = message.split("::");
        if (messageParts.length != 4) {
            throw new IllegalArgumentException(format("Provided string '%s' is not correctly formatted", message));
        }
        return new TimeTrackingEvent(
                Integer.parseInt(messageParts[0]),
                Integer.parseInt(messageParts[1]),
                TimeTrackingEventType.valueOf(messageParts[2]),
                LocalDateTime.ofEpochSecond(Long.parseLong(messageParts[3]), 0, ZoneOffset.UTC));

    }

    private long toSecond(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).getEpochSecond();
    }

}
