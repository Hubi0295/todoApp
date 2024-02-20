package io.github.hubi0295.reports;

import io.github.hubi0295.model.event.TaskEvent;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name ="task_events")
class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskId;
    String name;
    LocalDateTime occurence;

    public PersistedTaskEvent() {
    }
    PersistedTaskEvent(TaskEvent taskE){
        taskId=taskE.getTaskId();
        name=taskE.getClass().getSimpleName();
        occurence = LocalDateTime.ofInstant(taskE.getOccurence(), ZoneId.systemDefault());

    }
}
