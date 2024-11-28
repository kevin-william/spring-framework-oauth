package oauth2Jwt.controllers;

import oauth2Jwt.entities.Task;
import oauth2Jwt.services.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TaskController {

  @Autowired
  private ITaskService taskService;

  @PreAuthorize("hasAuthority('SCOPE_READ')")
  @GetMapping("/tasks")
  List<Task> GetAll(){
    return taskService.GetAllTasks();
  }

  @PreAuthorize("hasAuthority('SCOPE_WRITE')")
  @PostMapping("/tasks")
  Task SaveTask(@RequestBody Task task){
    return taskService.SaveTask(task);
  }

  @PreAuthorize("hasAuthority('SCOPE_WRITE')")
  @PutMapping("/tasks")
  Task UpdateTask(@RequestBody Task task){
    return taskService.UpdateTask(task);
  }

  @PreAuthorize("hasAuthority('SCOPE_READ')")
  @GetMapping("/tasks/{id}")
  Task GetById(@PathVariable long id){
    if(taskService.TaskExists(id)){
      return taskService.GetTaskById(id)
          .orElseThrow(() -> new IllegalArgumentException("Id inválido"));
    }
    return null;
  }

}

