package com.taskmanagment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanagment.DTO.UserDTO;

import com.taskmanagment.Entity.User;
import com.taskmanagment.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(User.Role.valueOf(userDTO.getRole().toUpperCase()));
        try {
            User.Role validRole = User.Role.valueOf(userDTO.getRole().toUpperCase());
            user.setRole(validRole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role. Allowed values: MANAGER, EMPLOYEE, ADMIN");
        }

        return userRepository.save(user);
    }

}
