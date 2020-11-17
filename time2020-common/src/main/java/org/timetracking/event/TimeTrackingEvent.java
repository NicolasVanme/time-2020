package org.timetracking.event;

import java.time.LocalDateTime;

public interface TimeTrackingEvent {

    int getEmployeeId();

    int getTaskId();

    TimeTrackingEventType getType();

    LocalDateTime getEventDateTime();

}
