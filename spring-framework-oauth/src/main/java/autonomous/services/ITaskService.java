package autonomous.services;

import autonomous.entities.Task;

import java.util.List;

public interface ITaskServices {

  Task GetTaskById(long id);
  List<Task> GetAllTasks();
  Task SaveTask(Task task);
  Task UpdateTAsk(Task task);
  boolean DeleteTaskById(long id);
  boolean TaskExists(long id);

}
