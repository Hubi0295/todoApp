package io.github.hubi0295.reports;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final TaskRepository repo;
    private  PersistedTaskEventRepository eventRepo;
    public ReportController(TaskRepository repo, PersistedTaskEventRepository eventRepo) {
        this.repo = repo;
        this.eventRepo = eventRepo;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id){
        return repo.findById(id)
                .map(task -> new TaskWithChangesCount(task,eventRepo.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;
        TaskWithChangesCount(Task task, List<PersistedTaskEvent> events) {
            description=task.getDescription();
            done=task.isDone();
            changesCount = events.size();
        }
    }
}
