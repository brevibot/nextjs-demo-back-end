package com.example.dashboardapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class TeamLeadApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "approval_request_id")
    private ApprovalRequest approvalRequest;

    private String approvedBy; // Team Lead's name or ID
    private LocalDateTime approvalDate;
    private boolean approved;
}