package io.github.hubi0295;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskGroup;
import io.github.hubi0295.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WarmUp implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger = LoggerFactory.getLogger(WarmUp.class);
    private final TaskGroupRepository groupRepository;

    WarmUp(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application WarmUp after context refreshed");
        String description="ApplicationContextEvent";
        if(!groupRepository.existsByDescription(description)){
        logger.info("No required group found! Adding it");
        var group = new TaskGroup();
        group.setDescription(description);
        group.setTasks(Set.of(new Task("ContextClosedEvent",null,group),
                new Task("ContextRefreshedEvent",null,group),
                new Task("ContextStoppedEvent",null,group),
                new Task("ContextStartedEvent",null,group)));
        groupRepository.save(group);
        }

    }
}
