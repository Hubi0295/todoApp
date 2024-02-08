package io.github.hubi0295.model;

import jakarta.persistence.*;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="desc")
    private String description;
    private boolean done;
    Task(){

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }
}
