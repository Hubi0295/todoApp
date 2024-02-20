package io.github.hubi0295.controller;

import io.github.hubi0295.logic.TaskService;
import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final ApplicationEventPublisher eventPublisher;
    private final TaskRepository repository;
    private final TaskService taskService;

    TaskController(ApplicationEventPublisher eventPublisher, TaskRepository repository, TaskService taskService) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
        this.taskService = taskService;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks() {
        logger.warn("Exposing all the tasks");
        return taskService.findAllAsync().thenApply((ResponseEntity::ok));
    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    @GetMapping("/test")
    void oldWay(HttpServletRequest req, HttpServletResponse res) throws IOException {
        logger.info(req.getParameter("URI"));
        res.getWriter().println("test old way");
    }
    @GetMapping("/search/done")
    ResponseEntity<List<Task>> readDoneTasks(@RequestParam(defaultValue = "true") boolean state){
        return ResponseEntity.ok(repository.findByDone(state));
    }
    @GetMapping("/{id}")
    ResponseEntity<Task> readOneTask(@PathVariable("id") int id) {
        logger.info("Get one Task by ID");
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> {
            task.updateFrom(toUpdate);
            repository.save(task);

        });
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<Task> createOneTask(@RequestBody @Valid Task toCreate) {
        logger.info("Create one task by id");
        Task task = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + task.getId())).body(task);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id).map(Task::toggle).ifPresent(eventPublisher::publishEvent);
        return ResponseEntity.noContent().build();
    }
}
