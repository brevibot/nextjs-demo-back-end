package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Profile("!local") // Only activate this component when the profile is NOT 'local'
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEventNotification(Event event, String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Upcoming Event Reminder: " + event.getTitle());
        message.setText(String.format(
            "Hello,\n\nThis is a reminder that the event '%s' is scheduled to start in 7 days on %s.\n\nThank you,\nThe Dashboard API",
            event.getTitle(),
            event.getStart().toLocalDate()
        ));
        mailSender.send(message);
    }
}