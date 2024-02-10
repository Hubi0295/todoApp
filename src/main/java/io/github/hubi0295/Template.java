package io.github.hubi0295;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("template")
public class Template extends TaskConfigurationProperties{

    public boolean isAllowMultipleTasksFromTemplate() {
        return isAllowMultipleTasksFromTemplate;
    }

    public void setAllowMultipleTasksFromTemplate(boolean allowMultipleTasksFromTemplate) {
        isAllowMultipleTasksFromTemplate = allowMultipleTasksFromTemplate;
    }
}
