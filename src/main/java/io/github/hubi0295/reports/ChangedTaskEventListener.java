package io.github.hubi0295.reports;

import io.github.hubi0295.model.event.TaskDone;
import io.github.hubi0295.model.event.TaskUndone;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
@Service
class ChangedTaskEventListener {
    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);
    @EventListener
    public void on(TaskDone event){
        logger.info("Got "+event);
    }
    public void on(TaskUndone event){
        logger.info("Got "+event);
    }

}
