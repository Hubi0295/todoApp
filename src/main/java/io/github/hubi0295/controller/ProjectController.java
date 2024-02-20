package io.github.hubi0295.controller;

import io.github.hubi0295.logic.ProjectService;
import io.github.hubi0295.model.Project;
import io.github.hubi0295.model.ProjectStep;
import io.github.hubi0295.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    String showProjects(Model model){
        model.addAttribute("project", new ProjectWriteModel());
        return "projects";
    }
    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel projectMemory){
        projectMemory.getSteps().add(new ProjectStep());
        return "projects";
    }
    @PostMapping
    String addProject(@ModelAttribute("project") @Valid ProjectWriteModel projectMemory, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "projects";
        }
        service.save(projectMemory);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("projects", getProjects());

        model.addAttribute("message", "Dodano projekt");
        return "projects";
    }
    @Timed(value = "project.create.group",histogram = true,percentiles = {0.5,0.95,0.99})
    @PostMapping("/{id}")
    String createGroup(@ModelAttribute("project")ProjectWriteModel current, Model model, @PathVariable int id, @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline){
        try{
            service.createGroup(deadline,id);
            model.addAttribute("message","Dodano:)!");

        }
        catch(IllegalStateException | IllegalArgumentException e){
            model.addAttribute("message","Nie dodano:(");
        }
        return "projects";

    }
    @ModelAttribute("projects")
    List<Project> getProjects(){
        return service.readAll();
    }

}
