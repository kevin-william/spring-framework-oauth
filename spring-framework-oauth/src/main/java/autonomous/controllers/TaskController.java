package autonomous.controllers;

import autonomous.entities.Task;
import autonomous.services.ITaskService;
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

  @GetMapping("/public")
  public String publicEndpoint() {
    return "Este endpoint é público.";
  }

  @GetMapping("/user")
  public String userEndpoint() {
    return "Olá, usuário! Este endpoint requer a role USER.";
  }

  @GetMapping("/admin")
  public String adminEndpoint() {
    return "Olá, administrador! Este endpoint requer a role ADMIN.";
  }

}

