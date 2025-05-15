package com.taskmanagment.DTO;

import com.taskmanagment.Entity.User;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class TaskDTO {
    private Long id;
    private String description;

    // For input
    private Long assignedToId;
    private Long assignedById;

    // For output
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
}
