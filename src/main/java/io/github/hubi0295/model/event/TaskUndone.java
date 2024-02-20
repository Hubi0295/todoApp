package io.github.hubi0295.model.event;

import io.github.hubi0295.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent {
    TaskUndone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());

    }
}
