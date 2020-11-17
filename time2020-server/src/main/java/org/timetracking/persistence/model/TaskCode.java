package org.timetracking.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findTaskCodeById",
                query = "from TaskCode t where t.id = :id"
        )
})

@Entity
@Table(name = "task_code")
public class TaskCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name",length = 30,nullable = false)
    private String name;
    @Column(name="comment")
    private String comment;

    public TaskCode() {
    }

    public TaskCode(String name) {
        this.name = name;
    }

    public TaskCode(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TaskCode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
