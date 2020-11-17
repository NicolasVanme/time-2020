package org.timetracking;

public class IllegalTimeTrackingEventException extends RuntimeException {
    public IllegalTimeTrackingEventException(String message) {
        super(message);
    }

    public static IllegalTimeTrackingEventException illegalEmployee(int employeeId){
        return new IllegalTimeTrackingEventException("Illegal Employee with Id:" + employeeId);
    }

    public static IllegalTimeTrackingEventException illegalTask(int taskId){
        return new IllegalTimeTrackingEventException("Illegal Task with Id:" + taskId);
    }
}
