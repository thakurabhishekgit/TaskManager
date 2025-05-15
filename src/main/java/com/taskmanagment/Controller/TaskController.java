package com.taskmanagment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagment.DTO.TaskDTO;
import com.taskmanagment.Entity.Task;
import com.taskmanagment.Services.TaskService;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/getTaskAssigenedByUser/{userId}")
    public ResponseEntity<List<Task>> getTaskAssigenedByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTaskAssigenedByUser(userId));
    }

    @GetMapping("/getMyTasks/{userId}")
    public ResponseEntity<List<Task>> getMyTaskss(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getMyTasks(userId));
    }

}
