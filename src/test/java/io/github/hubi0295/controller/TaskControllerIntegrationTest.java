package io.github.hubi0295.controller;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository repo;
    @Test
    void httpGEt_returnsGivenTask() throws Exception {
        //given
        int id =repo.save(new Task("description", LocalDateTime.now())).getId();
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/"+id)).andExpect(status().is2xxSuccessful());
    }
}
