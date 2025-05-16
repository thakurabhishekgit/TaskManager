package com.taskmanagment.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagment.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(UUID userId);

    List<Task> findByAssignedById(UUID userId);

}
