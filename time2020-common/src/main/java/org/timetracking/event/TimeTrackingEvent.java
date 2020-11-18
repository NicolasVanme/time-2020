package org.timetracking.event;

import java.time.LocalDateTime;

public class TimeTrackingEvent {

    private final int employeeId;
    private final int taskId;
    private final TimeTrackingEventType type;
    private final LocalDateTime eventDateTime;

    public TimeTrackingEvent(int employeeId, int taskId, TimeTrackingEventType type, LocalDateTime eventDateTime) {
        this.employeeId = employeeId;
        this.taskId = taskId;
        this.type = type;
        this.eventDateTime = eventDateTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public TimeTrackingEventType getType() {
        return type;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }
}
