package oauth2Jwt.controllers;

import oauth2Jwt.entities.Task;
import oauth2Jwt.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

  @Autowired
  private ITaskService taskService;

  @GetMapping("/tasks")
  List<Task> GetAll(){
    return taskService.GetAllTasks();
  }

  @PostMapping("/tasks")
  Task SaveTask(@RequestBody Task task){
    return taskService.SaveTask(task);
  }

  @PutMapping("/tasks")
  Task UpdateTask(@RequestBody Task task){
    return taskService.UpdateTask(task);
  }

  @GetMapping("/tasks/{id}")
  Task GetById(@PathVariable long id){
    if(taskService.TaskExists(id)){
      return taskService.GetTaskById(id)
          .orElseThrow(() -> new IllegalArgumentException("Id inválido"));
    }
    return null;
  }

}

