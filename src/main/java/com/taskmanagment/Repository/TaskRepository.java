package com.taskmanagment.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagment.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);

    List<Task> findByAssignedById(Long userId);

}
