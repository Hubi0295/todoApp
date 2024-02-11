package io.github.hubi0295.model;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(Integer id);
    boolean existsById(Integer id);
    Task save(Task entity);
    Page<Task> findAll(Pageable page);
    List<Task> findByDone( boolean done);
}
