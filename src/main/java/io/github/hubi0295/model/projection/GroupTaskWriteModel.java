package io.github.hubi0295.model.projection;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskGroup;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    @NotBlank(message = "U can not post blank desc group")

    private String description;
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public Task toTask(TaskGroup group){
        return new Task(description,deadline,group);

    }
}
