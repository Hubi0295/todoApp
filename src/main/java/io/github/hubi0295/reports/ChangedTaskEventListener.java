package io.github.hubi0295.reports;

import io.github.hubi0295.model.event.TaskDone;
import io.github.hubi0295.model.event.TaskUndone;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
@Service
class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    private PersistedTaskEventRepository repo;

    public ChangedTaskEventListener(PersistedTaskEventRepository repo) {
        this.repo = repo;
    }

    @Async
    @EventListener
    public void on(TaskDone event){
        logger.info("Got Done "+event);
        repo.save(new PersistedTaskEvent(event));
    }
    @Async
    @EventListener
    public void on(TaskUndone event){
        logger.info("Got Undone"+event);
        repo.save(new PersistedTaskEvent(event));;
    }


}
