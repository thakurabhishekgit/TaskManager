package com.taskmanagment.Controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<?> createTask(
            @RequestBody TaskDTO taskDTO,
            @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        try {
            Task createdTask = taskService.createTask(taskDTO, token);
            return ResponseEntity.ok(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/getTaskAssigenedByUser/{userId}")
    public ResponseEntity<List<Task>> getTaskAssigenedByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(taskService.getTaskAssigenedByUser(userId));
    }

    @GetMapping("/getMyTasks/{userId}")
    public ResponseEntity<List<Task>> getMyTaskss(@PathVariable UUID userId) {
        return ResponseEntity.ok(taskService.getMyTasks(userId));
    }

}
