package autonomous.repositories;

import autonomous.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
  boolean existsById(long id);
}
