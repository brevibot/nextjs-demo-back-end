package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.Notification;
import com.example.dashboardapi.repository.NotificationRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // New endpoint for the frontend to get the current banner
    @GetMapping("/active")
    public ResponseEntity<Notification> getActiveNotification() {
        Optional<Notification> notification = notificationRepository.findFirstByActiveTrueOrderByCreatedAtDesc();
        return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
    
    // Updated endpoint to save the banner message to the database
    @PostMapping
    @Transactional
    public ResponseEntity<?> handleNotification(@RequestBody NotificationRequest request) {
        // Deactivate all existing notifications
        notificationRepository.findAll().forEach(n -> n.setActive(false));
        
        // Save the new notification
        Notification newNotification = new Notification(request.getMessage());
        notificationRepository.save(newNotification);

        System.out.println("--- NOTIFICATION SET ---");
        System.out.printf("New active notification: \"%s\"%n", request.getMessage());
        System.out.println("-------------------------");
        
        return ResponseEntity.ok(Map.of("success", true, "message", "Notification has been set globally."));
    }

    // New endpoint to clear the banner
    @PostMapping("/clear")
    @Transactional
    public ResponseEntity<?> clearNotification() {
        notificationRepository.findAll().forEach(n -> n.setActive(false));
        System.out.println("--- ALL NOTIFICATIONS CLEARED ---");
        return ResponseEntity.ok(Map.of("success", true, "message", "All notifications cleared."));
    }

    @Data
    static class NotificationRequest {
        private String message;
    }
}