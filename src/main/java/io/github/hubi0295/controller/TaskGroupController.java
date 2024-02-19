package io.github.hubi0295.controller;

import io.github.hubi0295.logic.TaskGroupService;
import io.github.hubi0295.model.ProjectStep;
import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import io.github.hubi0295.model.projection.GroupReadModel;
import io.github.hubi0295.model.projection.GroupTaskWriteModel;
import io.github.hubi0295.model.projection.GroupWriteModel;
import io.github.hubi0295.model.projection.ProjectWriteModel;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class TaskGroupController {

    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository repository;
    TaskGroupController(final TaskGroupService service, final TaskRepository repository) {
        this.service = service;
        this.repository = repository;
    }
    @PostMapping(params = "addTask",produces = MediaType.TEXT_HTML_VALUE,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel groupWriteModel){
        groupWriteModel.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    String addGroup(@ModelAttribute("group") @Valid GroupWriteModel groupWriteModel, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "groups";
        }
        service.createGroup(groupWriteModel);
        model.addAttribute("group", new ProjectWriteModel());
        model.addAttribute("groups", getGroups());

        model.addAttribute("message", "Dodano Grupe");
        return "groups";
    }
    @ModelAttribute("groups")

    public List<GroupReadModel> getGroups() {
        return service.readAll();
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model){
        model.addAttribute("group",new GroupWriteModel());
        return "groups";
    }
    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(service.readAll());
    }
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }
    @ResponseBody
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegaAgrumentEx(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegaStateEx(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());

    }
}
