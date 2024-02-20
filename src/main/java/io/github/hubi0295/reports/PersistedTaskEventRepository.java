package io.github.hubi0295.reports;

import io.github.hubi0295.model.event.TaskEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent,Integer> {
    List<PersistedTaskEvent> findByTaskId(int task);
}
