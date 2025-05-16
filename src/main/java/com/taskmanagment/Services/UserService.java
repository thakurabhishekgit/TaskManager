package com.taskmanagment.Services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanagment.DTO.UserDTO;
import com.taskmanagment.Entity.User;
import com.taskmanagment.Entity.User.Role;
import com.taskmanagment.Repository.UserRepository;
import com.taskmanagment.utils.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        try {
            Role roleEnum = Role.valueOf(userDTO.getRole().toUpperCase());
            user.setRole(roleEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid role: " + userDTO.getRole() + ". Allowed roles: EMPLOYEE, ADMIN, MANAGER");
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);

        String token = JwtUtil.generateToken(user.getEmail());

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getPassword(),
                token);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;

        if (userDTO.getName() != null)
            user.setName(userDTO.getName());
        if (userDTO.getEmail() != null)
            user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null)
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getRole() != null) {
            Role roleEnum = Role.valueOf(userDTO.getRole().toUpperCase());
            user.setRole(roleEnum);
        }

        return userRepository.save(user);
    }

    public User userLogin(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

}
