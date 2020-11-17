package org.timetracking.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@NamedQueries({
        @NamedQuery(
                name = "findEmployeeById",
                query = "from Employee e where e.id = :id"
        )
})
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="first_name",length = 30,nullable = false)
    private String firstName;
    @Column(name="last_name",length = 40,nullable = false)
    private String lastName;
    @Column(name="start",nullable = false)
    private LocalDateTime start;
    @Column(name="stop")
    private LocalDateTime stop;

    public Employee() {    }

    public Employee(String firstName, String lastName, LocalDateTime start) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.start = start;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
