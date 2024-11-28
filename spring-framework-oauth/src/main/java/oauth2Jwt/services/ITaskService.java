package oauth2Jwt.services;

import oauth2Jwt.entities.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {

  Optional<Task> GetTaskById(long id);
  List<Task> GetAllTasks();
  Task SaveTask(Task task);
  Task UpdateTask(Task task);
  void DeleteTaskById(long id);
  boolean TaskExists(long id);

}
