package io.github.hubi0295.controller;

import io.github.hubi0295.logic.ProjectService;
import io.github.hubi0295.model.Project;
import io.github.hubi0295.model.ProjectStep;
import io.github.hubi0295.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    String addProject(@ModelAttribute("project") ProjectWriteModel projectMemory, Model model){
        service.save(projectMemory);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message", "Dodano projekt");
        return "projects";
    }
    @ModelAttribute("projects")
    List<Project> getProjects(){
        return service.readAll();
    }

}
