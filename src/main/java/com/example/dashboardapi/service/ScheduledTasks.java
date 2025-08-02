package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Event;
import com.example.dashboardapi.repository.EventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {

    private final EventRepository eventRepository;
    private final EmailService emailService;

    public ScheduledTasks(EventRepository eventRepository, EmailService emailService) {
        this.eventRepository = eventRepository;
        this.emailService = emailService;
    }

    // A hardcoded list of recipient emails for demonstration purposes
    private final List<String> recipientEmails = List.of("user1@example.com", "user2@example.com");

    @Scheduled(cron = "0 0 9 * * ?") // Runs every day at 9 AM
    public void checkForUpcomingEvents() {
        LocalDate sevenDaysFromNow = LocalDate.now().plusDays(7);
        List<Event> upcomingEvents = eventRepository.findAll();

        for (Event event : upcomingEvents) {
            if (event.getStart().toLocalDate().isEqual(sevenDaysFromNow)) {
                System.out.printf("Sending notification for event: '%s'%n", event.getTitle());
                for (String email : recipientEmails) {
                    emailService.sendEventNotification(event, email);
                }
            }
        }
    }
}