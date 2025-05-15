package com.taskmanagment.DTO;

import com.taskmanagment.Entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String description;
    private UserDTO assignedTo;
    private UserDTO assignedBy;

    public TaskDTO(Long id, String description, User assignedTo, User assignedBy) {
        this.id = id;
        this.description = description;
        this.assignedTo = new UserDTO(assignedTo.getId(), assignedTo.getName(), assignedTo.getEmail(),
                assignedTo.getRole().toString());
        this.assignedBy = new UserDTO(assignedBy.getId(), assignedBy.getName(), assignedBy.getEmail(),
                assignedBy.getRole().toString());
    }

    // getters and setters
}
