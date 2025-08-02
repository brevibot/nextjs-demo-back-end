package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "notifications", path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Custom query to find the single active notification
    Optional<Notification> findFirstByActiveTrueOrderByCreatedAtDesc();
}