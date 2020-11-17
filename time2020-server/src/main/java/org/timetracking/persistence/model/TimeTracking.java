package org.timetracking.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(
                name = "findStartedTimeTrackingByEmployeeId",
                query = "from TimeTracking t where t.employee.id = :employeeId and t.stop is null"
        )
})
@Entity
@Table(name="time_tracking")
public class TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", foreignKey=@ForeignKey(name = "fk_time_tracking_to_employee"), nullable = false)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", foreignKey=@ForeignKey(name = "fk_time_tracking_to_task"), nullable = false)
    private Task task;
    @Column(name="start", nullable = false)
    private LocalDateTime start;
    @Column(name="stop")
    private LocalDateTime stop;
    @Column(name="comment")
    private String comment;

    public int getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStop() {
        return stop;
    }

    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TimeTracking{" +
                "id=" + id +
                ", employee=" + employee +
                ", task=" + task +
                ", start=" + start +
                ", stop=" + stop +
                ", comment='" + comment + '\'' +
                '}';
    }
}
