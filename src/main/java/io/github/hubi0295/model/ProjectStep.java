package io.github.hubi0295.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="project_steps")
public class ProjectStep {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="description")
    @NotBlank(message="U can not post blank project step description")
    private String description;
    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    Project getProject() {
        return project;
    }

    void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
