package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.Notification;
import com.example.dashboardapi.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Import this
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false) // Add this annotation
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationRepository notificationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getActiveNotification_ShouldReturnNotification_WhenOneIsActive() throws Exception {
        // Arrange
        Notification activeNotification = new Notification("Test active banner");
        activeNotification.setId(1L);
        activeNotification.setCreatedAt(LocalDateTime.now());

        when(notificationRepository.findFirstByActiveTrueOrderByCreatedAtDesc()).thenReturn(Optional.of(activeNotification));

        // Act & Assert
        mockMvc.perform(get("/api/notifications/active"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Test active banner"));
    }

    @Test
    void getActiveNotification_ShouldReturnNoContent_WhenNoneIsActive() throws Exception {
        // Arrange
        when(notificationRepository.findFirstByActiveTrueOrderByCreatedAtDesc()).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/notifications/active"))
                .andExpect(status().isNoContent());
    }

    @Test
    void handleNotification_ShouldDeactivateOldAndSaveNewNotification() throws Exception {
        // Arrange
        Notification oldNotification = new Notification("Old message");
        when(notificationRepository.findAll()).thenReturn(Collections.singletonList(oldNotification));

        NotificationController.NotificationRequest request = new NotificationController.NotificationRequest();
        request.setMessage("New test message");

        // Act & Assert
        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Verify that the repository was called to deactivate old notifications and save the new one.
        verify(notificationRepository).findAll();
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void clearNotification_ShouldDeactivateAllNotifications() throws Exception {
        // Arrange
        Notification oldNotification = new Notification("An active message");
        when(notificationRepository.findAll()).thenReturn(Collections.singletonList(oldNotification));

        // Act & Assert
        mockMvc.perform(post("/api/notifications/clear"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        
        // Verify that the repository was called to find and deactivate all notifications.
        verify(notificationRepository).findAll();
    }
}