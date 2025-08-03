package com.example.dashboardapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TeamLeadChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "approval_request_id")
    @JsonBackReference("approvalRequest-teamLeadChanges")
    private ApprovalRequest approvalRequest;

    private String changeDescription;
    private String ticketNumber;
    private String reason; // "Code Fix", "New Requirement", "Change in Requirement"
    private String impactDescription;
    private String submittedBy; // Team Leader's name or ID
}