package io.github.hubi0295.controller;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(TaskController.class)
public class TaskControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskRepository repo;

    @Test
    void httpGEt_returnsGivenTask() throws Exception {
        //given
        when(repo.findById(anyInt())).thenReturn(Optional.of(new Task("blablabla", LocalDateTime.now())));
        //expect
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/123"))
                .andDo(print())
                .andExpect(content().string(containsString("blablabla")));
    }
}
