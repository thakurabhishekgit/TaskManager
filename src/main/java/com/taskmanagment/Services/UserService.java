package com.taskmanagment.Services;

import java.util.List;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user == null) {
            return null;
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null) {
            Role roleEnum = Role.valueOf(userDTO.getRole().toUpperCase());
            user.setRole(roleEnum);
        }
        return userRepository.save(user);
    }

}
