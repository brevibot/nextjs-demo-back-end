package com.example.dashboardapi.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @PostMapping
    public ResponseEntity<?> handleNotification(@RequestBody NotificationRequest request) {
        // In a real application, you would trigger an email service here.
        System.out.println("--- MOCK EMAIL SENDER ---");
        System.out.printf("Received notification to send: \"%s\"%n", request.getMessage());
        System.out.println("-------------------------");
        
        // Return a success response
        return ResponseEntity.ok(Map.of("success", true, "message", "Notification processed and email sent."));
    }

    // A simple DTO (Data Transfer Object) to map the incoming JSON
    @Data
    static class NotificationRequest {
        private String message;
    }
}