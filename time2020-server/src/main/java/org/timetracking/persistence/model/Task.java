package org.timetracking.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findTaskById",
                query = "from Task t where t.id = :id"
        )
})
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "fk_task_to_project"), nullable = false)
    Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_code_id", foreignKey = @ForeignKey(name = "fk_task_to_task_code"), nullable = true) // TODO - should be false
    TaskCode taskCode;
    @Column(name = "closed")
    Boolean closed;
    @Column(name = "comment")
    String comment;

    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public TaskCode getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(TaskCode taskCode) {
        this.taskCode = taskCode;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", project=" + project +
                ", taskCode=" + taskCode +
                ", comment='" + comment + '\'' +
                '}';
    }
}
