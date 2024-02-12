package io.github.hubi0295.logic;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TempService {
    @Autowired
    List<String> temp(TaskGroupRepository repo){
        return repo.findAll().stream().flatMap(taskGroup -> taskGroup.getTasks().stream()).map(Task::getDescription ).collect(Collectors.toList());
    }
}
