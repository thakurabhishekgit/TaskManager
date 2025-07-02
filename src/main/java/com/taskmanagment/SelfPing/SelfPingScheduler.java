package com.taskmanagment.SelfPing;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SelfPingScheduler {

    private static final String PING_URL = "http://localhost:1001/health";

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void pingServer() {
        try {
            String response = restTemplate.getForObject(PING_URL, String.class);
            System.out.println("[SelfPingScheduler] Pinged server at " + PING_URL + " | Response: " + response);
        } catch (Exception e) {
            System.out.println("[SelfPingScheduler] Failed to ping server: " + e.getMessage());
        }
    }
}