package oauth2Jwt.repositories;

import oauth2Jwt.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
  boolean existsById(long id);
}
