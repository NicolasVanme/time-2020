package org.timetracking;

import org.timetracking.event.TimeTrackingEvent;
import org.timetracking.event.TimeTrackingEventException;
import org.timetracking.event.TimeTrackingEventType;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeTrackingEventCommandLineReader {

    public TimeTrackingEvent read(String[] args) {
        validateCommandLine(args);

        return new TimeTrackingEvent(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[2]),
                TimeTrackingEventType.valueOf(args[1].toUpperCase()),
                LocalDateTime.now()
                );
    }

    private void validateCommandLine(String[] args) {
        if (args == null || args.length != 3) {
            throw new TimeTrackingEventException("Three arguments expected");
        }
        if (!args[0].matches("[0-9]+")) {
            throw new TimeTrackingEventException("Employee ID expected in first argument");
        }
        try {
            TimeTrackingEventType.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            String eventTypeValues = Stream.of(TimeTrackingEventType.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new TimeTrackingEventException("Expected value in second arguments are: " + eventTypeValues, e);
        }
        if (!args[2].matches("[0-9]+")) {
            throw new TimeTrackingEventException("Task ID expected in third argument");
        }
    }

}
