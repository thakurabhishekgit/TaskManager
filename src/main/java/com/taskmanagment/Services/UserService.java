package com.taskmanagment.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanagment.DTO.UserDTO;
import com.taskmanagment.Entity.User;
import com.taskmanagment.Entity.User.Role;
import com.taskmanagment.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(UserDTO userDTO) {
        User user = new User();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        // Convert role string to enum with uppercase (handle invalid role)
        try {
            Role roleEnum = Role.valueOf(userDTO.getRole().toUpperCase());
            user.setRole(roleEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid role: " + userDTO.getRole() + ". Allowed roles: EMPLOYEE, ADMIN, MANAGER");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }
}
