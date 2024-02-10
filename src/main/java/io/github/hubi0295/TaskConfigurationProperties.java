package io.github.hubi0295;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    public boolean isAllowMultipleTasksFromTemplate;

}
