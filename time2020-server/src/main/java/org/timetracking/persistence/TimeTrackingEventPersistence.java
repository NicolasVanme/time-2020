package org.timetracking.persistence;

import org.timetracking.IllegalTimeTrackingEventException;
import org.timetracking.event.TimeTrackingEvent;
import org.timetracking.event.TimeTrackingEventListener;
import org.timetracking.event.TimeTrackingEventType;
import org.timetracking.persistence.model.Employee;
import org.timetracking.persistence.model.Task;
import org.timetracking.persistence.model.TimeTracking;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Class that will listen for time tracking event and save them in the database
 */
public class TimeTrackingEventPersistence implements TimeTrackingEventListener {

    private final PersistenceService persistenceService;

    public TimeTrackingEventPersistence(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public void onEvent(TimeTrackingEvent timeTrackingEvent) {
        if (timeTrackingEvent.getType() == TimeTrackingEventType.START) {
            startTimeTracking(timeTrackingEvent);
        } else {
            stopTimeTracking(timeTrackingEvent);
        }
    }

    private void startTimeTracking(TimeTrackingEvent timeTrackingEvent) {
        long alreadyStartedTasks = findStartedTimeTrackingByEmployeeId(timeTrackingEvent.getEmployeeId()).count();
        if (alreadyStartedTasks > 0) {
            System.out.println("WARNING : Employee " + timeTrackingEvent.getEmployeeId() + " has already " + alreadyStartedTasks + " started task(s) !");
        }

        System.out.println("Saving received Time Tracking event...");

        TimeTracking timeTracking = new TimeTracking();
        timeTracking.setEmployee(findEmployee(timeTrackingEvent.getEmployeeId()));
        timeTracking.setTask(findTask(timeTrackingEvent.getTaskId()));
        timeTracking.setStart(timeTrackingEvent.getEventDateTime());

        persistenceService.save(timeTracking);

        System.out.println("Time Tracking event successfully saved !");
    }

    private void stopTimeTracking(TimeTrackingEvent timeTrackingEvent) {
        findStartedTimeTrackingByEmployeeId(timeTrackingEvent.getEmployeeId())
                .filter(timeTracking -> timeTrackingEvent.getTaskId() == timeTracking.getTask().getId())
                .findFirst()
                .ifPresentOrElse(startedTimeTracking -> {
                    startedTimeTracking.setStop(timeTrackingEvent.getEventDateTime());
                    persistenceService.save(startedTimeTracking);
                }, () -> System.out.println("WARNING : Employee " + timeTrackingEvent.getEmployeeId() + " want to stop not started task " + timeTrackingEvent.getTaskId() + " !"));
    }

    private Stream<TimeTracking> findStartedTimeTrackingByEmployeeId(int employeeId) {
        return this.persistenceService
                .findStartedTimeTrackingByEmployeeId(employeeId)
                .stream();
    }

    private Employee findEmployee(int employeeId) {
        Optional<Employee> employee = persistenceService.findEmployeeById(employeeId);
        if (employee.isEmpty()){
            throw IllegalTimeTrackingEventException.illegalEmployee(employeeId);
        }
        return employee.get();
    }

    private Task findTask(int taskId) {
        return persistenceService.findTaskById(taskId)
                .orElseThrow(()->IllegalTimeTrackingEventException.illegalTask(taskId));
    }
}
