package io.github.hubi0295.controller;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class TaskControllerTestE2ETest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks(){
        //given
        int size = repo.findAll().size();
        repo.save(new Task("pierwsze", LocalDateTime.now()));
        repo.save(new Task("drugie", LocalDateTime.now()));
        //when
        Task[] tasksRecived = this.restTemplate.getForObject("http://localhost:" + this.port + "/tasks", Task[].class);
        //then
        assertThat(tasksRecived).hasSize(size+2);
    }

}