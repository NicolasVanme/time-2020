package org.timetracking.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findProjectById",
                query = "from Project p where p.id = :id"
        ),
        @NamedQuery(
                name = "findProjectByName",
                query = "from Project p where p.name = :name"
        )
})
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 40, nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_center_id", foreignKey = @ForeignKey(name = "fk_project_to_cost_center"))
    private CostCenter costCenter;
    @Column(name = "comment")
    private String comment;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", costCenter=" + costCenter +
                ", comment='" + comment + '\'' +
                '}';
    }
}
