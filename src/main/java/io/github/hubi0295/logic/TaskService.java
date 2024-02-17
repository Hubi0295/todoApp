package io.github.hubi0295.logic;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    public static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }
    @Async
    public CompletableFuture<List<Task>> findAllAsync(){
        logger.warn("Hey you are async");
        return CompletableFuture.supplyAsync(taskRepo::findAll);
    }
}
