package io.github.hubi0295.controller;

import io.github.hubi0295.logic.TaskGroupService;
import io.github.hubi0295.model.TaskGroup;
import io.github.hubi0295.model.TaskGroupRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class TaskGroupController {
    private final TaskGroupRepository taskGroupRepository;
    private final TaskGroupService taskGroupService;

    public TaskGroupController(TaskGroupRepository taskGroupRepository, TaskGroupService taskGroupService) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
    }
    @GetMapping
    ResponseEntity<List<TaskGroup>> getAllGroups(){
        return ResponseEntity.ok(taskGroupRepository.findAll());
    }
    //TODO Post put patch mapping
}
