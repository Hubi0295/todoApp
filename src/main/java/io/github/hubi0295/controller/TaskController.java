package io.github.hubi0295.controller;
import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Controller
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    TaskController(TaskRepository repository) {
        this.repository = repository;
    }
    @RequestMapping(value = "/tasks", params={"!sort","!page","!size"},method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTasks(){
        logger.warn("Exposing all the tasks");
        return ResponseEntity.ok(repository.findAll());
    }
    @RequestMapping(value="/tasks",method = RequestMethod.GET)
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.info("Custom pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
    @RequestMapping(value="/tasks/{id}",method = RequestMethod.GET)
    ResponseEntity<Task> readOneTask(@PathVariable("id") int id){
        logger.info("Get one Task by ID");
        return  repository.findById(id)
                .map(task->ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }
    @RequestMapping(value="/tasks/{id}",method = RequestMethod.PUT)
    ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody @Valid Task toUpdate){
        if(!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value="/tasks",method = RequestMethod.POST)
     ResponseEntity<Task> createOneTask(@RequestBody @Valid Task toCreate){
        logger.info("Create one task by id");
        Task task = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/"+task.getId())).body(task);
    }
}
