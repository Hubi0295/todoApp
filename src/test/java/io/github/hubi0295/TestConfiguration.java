package io.github.hubi0295;

import io.github.hubi0295.model.Task;
import io.github.hubi0295.model.TaskGroup;
import io.github.hubi0295.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
class TestConfiguration {
    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:test","sa","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }
    @Bean
    @Primary
    @Profile("integration")
    TaskRepository testRepo() {
        return new TaskRepository() {
            private Map<Integer, Task> tasks = new HashMap<>();

            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public Page<Task> findAll(final Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(final Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public boolean existsById(final Integer id) {
                return tasks.containsKey(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(final Integer groupId) {
                return false;
            }

            @Override
            public List<Task> findAllByGroup_Id(Integer groupId) {
                return List.of();
            }

            @Override
            public List<Task> findByDone(final boolean done) {
                return null;
            }

            @Override
            public Task save(final Task entity){
                int key = tasks.size() + 1;
                Field field = null;
                try {
                    field = Task.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity,key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                tasks.put(key, entity);
                return tasks.get(key);
            }
        };
    }
}
