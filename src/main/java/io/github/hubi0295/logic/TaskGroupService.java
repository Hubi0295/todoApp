package io.github.hubi0295.logic;

import io.github.hubi0295.model.Project;
import io.github.hubi0295.model.TaskGroup;
import io.github.hubi0295.model.TaskGroupRepository;
import io.github.hubi0295.model.TaskRepository;
import io.github.hubi0295.model.projection.GroupReadModel;
import io.github.hubi0295.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source,null);
    }
    GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll(){
        return repository.findAll().stream().map(GroupReadModel::new).collect(Collectors.toList());
    }
    public void toggleGroup(int id){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(id)){
            throw new IllegalStateException("Group has undone tasks");
        }

        TaskGroup result = repository.findById(id).orElseThrow(()->new IllegalArgumentException("Task group with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
