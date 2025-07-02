package com.taskmanagment.SelfPing;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    // This endpoint is used to check the health of the application
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        System.out.println("[HealthController] Health check endpoint hit " + LocalDateTime.now());
        return ResponseEntity.ok("Application is running");
    }

}