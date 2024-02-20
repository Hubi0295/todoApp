package io.github.hubi0295.model.event;

import io.github.hubi0295.model.Task;

import java.time.Clock;
import java.time.Instant;

public abstract class TaskEvent {
    public static TaskEvent changed(Task source){
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);
    }
    private int taskId;
    private Instant occurence;

    TaskEvent(int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurence = Instant.now(clock);
    }

    public int getTaskId() {
        return taskId;
    }

    public Instant getOccurence() {
        return occurence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "taskId=" + taskId +
                ", occurence=" + occurence +
                '}';
    }
}
