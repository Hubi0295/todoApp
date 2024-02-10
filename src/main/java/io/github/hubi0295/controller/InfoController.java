package io.github.hubi0295.controller;

import io.github.hubi0295.TaskConfigurationProperties;
import io.github.hubi0295.Template;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class InfoController {
    private DataSourceProperties dataSource;
    private Template myProp;

    InfoController(DataSourceProperties dataSource, Template myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url(){
        return dataSource.getUrl();
    }
    @GetMapping("/info/prop")
    boolean myProp(){
        return myProp.isAllowMultipleTasksFromTemplate();
    }
}
