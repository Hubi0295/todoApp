package io.github.hubi0295.logic;

import io.github.hubi0295.TaskConfigurationProperties;
import io.github.hubi0295.model.*;
import io.github.hubi0295.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskRepository, TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskRepository = taskRepository;
        this.config = config;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }
    public Project save(Project toSave){
        return repository.save(toSave);
    }
    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasks() && taskRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result=repository.findById(projectId).map(project -> {
            var targetGroup = new TaskGroup();
            targetGroup.setDescription(project.getDescription());
            targetGroup.setTasks(project
                    .getSteps().stream()
                    .map(step->new Task(step.getDescription(), deadline.plusDays(step.getDaysToDeadline())))
                    .collect(Collectors.toSet()));
            targetGroup.setProject(project);
            return taskRepository.save(targetGroup);
        }).orElseThrow(()->new IllegalArgumentException("project with given id is not found"));
    return new GroupReadModel(result);
    }
}
