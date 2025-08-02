package com.example.dashboardapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hash;
    private String author;
    private String message;

    public Change(String hash, String author, String message) {
        this.hash = hash;
        this.author = author;
        this.message = message;
    }
}
