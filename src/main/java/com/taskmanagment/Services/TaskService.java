package com.taskmanagment.Services;

import java.util.List;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagment.DTO.TaskDTO;

import com.taskmanagment.Entity.Task;
import com.taskmanagment.Entity.User;
import com.taskmanagment.Repository.TaskRepository;
import com.taskmanagment.Repository.UserRepository;
import com.taskmanagment.utils.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Task createTask(TaskDTO taskDTO, String token) {
        String email = JwtUtil.extractEmail(token);
        User assignedBy = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!assignedBy.getRole().equals(User.Role.MANAGER)) {
            throw new RuntimeException("Only MANAGER can assign tasks");
        }

        User assignedTo = userRepository.findById(taskDTO.getAssignedToId())
                .orElseThrow(() -> new RuntimeException("AssignedTo user not found"));

        Task task = new Task();
        task.setDescription(taskDTO.getDescription());
        task.setAssignedBy(assignedBy);
        task.setAssignedTo(assignedTo);

        return taskRepository.save(task);
    }

    @Transactional
    public List<Task> getTaskAssigenedByUser(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getTasksAssignedByMe();
    }

    @Transactional
    public List<Task> getMyTasks(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getTasksAssignedToMe();
    }
}
