package com.taskmanagment.Repository;

import java.util.Optional;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagment.Entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
