package com.example.dashboardapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ManagerApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "approval_request_id")
    @JsonBackReference("approvalRequest-managerApprovals")
    private ApprovalRequest approvalRequest;

    private String approvedBy; // Manager's name or ID
    private LocalDateTime approvalDate;
    private boolean approved;
}