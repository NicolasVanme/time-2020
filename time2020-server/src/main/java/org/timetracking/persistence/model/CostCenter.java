package org.timetracking.persistence.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "findCostCenterById",
                query = "from CostCenter c where c.id = :id"
        ),
        @NamedQuery(
                name = "findCostCenterByName",
                query = "from CostCenter c where c.name = :name"
        )
})
@Entity
@Table(name = "cost_center")
public class CostCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "comment")
    private String comment;

    public CostCenter() {
    }

    public CostCenter(String name) {
        this.name = name;
    }

    public CostCenter(String name, String comment) {
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
        return "CostCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
