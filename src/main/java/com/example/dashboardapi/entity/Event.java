package com.example.dashboardapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private boolean allDay;
    private String color;

    public Event(String title, ZonedDateTime start, ZonedDateTime end, boolean allDay, String color) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.color = color;
    }
}