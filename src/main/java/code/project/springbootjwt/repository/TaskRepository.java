package code.project.springbootjwt.repository;

import code.project.springbootjwt.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
