package oauth2Jwt.services;

import oauth2Jwt.entities.Task;
import oauth2Jwt.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

  @Autowired
  private ITaskRepository taskRepository;
  /**
   * @param id
   * @return
   */
  @Override
  public Optional<Task> GetTaskById(long id) {
    return taskRepository.findById(id);
  }

  /**
   * @return
   */
  @Override
  public List<Task> GetAllTasks() {
    return taskRepository.findAll();
  }

  /**
   * @param task
   * @return
   */
  @Override
  public Task SaveTask(Task task) {
    return taskRepository.save(task);
  }

  /**
   * @param task
   * @return
   */
  @Override
  public Task UpdateTask(Task task) {
    return taskRepository.save(task);
  }

  /**
   * @param id
   */
  @Override
  public void DeleteTaskById(long id) {
    taskRepository.deleteById(id);
  }

  /**
   * @param id
   * @return
   */
  @Override
  public boolean TaskExists(long id) {
    return taskRepository.existsById(id);
  }
}
