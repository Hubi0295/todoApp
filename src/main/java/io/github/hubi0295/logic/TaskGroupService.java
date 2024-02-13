package io.github.hubi0295.logic;

import io.github.hubi0295.TaskConfigurationProperties;
import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskGroup;
import io.github.hubi0295.model.TaskGroupRepository;
import io.github.hubi0295.model.TaskRepository;
import io.github.hubi0295.model.projection.GroupReadModel;
import io.github.hubi0295.model.projection.GroupWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll(){
        return repository.findAll().stream().map(GroupReadModel::new).collect(Collectors.toList());
    }
    public void toggleGroup(int id){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(id)){
            throw new IllegalStateException("Group has undone tasks");
        }
        else{
            TaskGroup result = repository.findById(id).orElseThrow(()->new IllegalArgumentException("Task group with given id not found"));
            result.setDone(!result.isDone());

        repository.save(result);
    }}
}
