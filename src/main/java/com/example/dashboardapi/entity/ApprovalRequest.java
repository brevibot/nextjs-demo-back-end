package com.example.dashboardapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "build_id")
    private Build build;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalRequest")
    private List<TeamLeadChange> teamLeadChanges;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalRequest")
    private List<QaApproval> qaApprovals;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalRequest")
    private List<ManagerApproval> managerApprovals;

    private String status; // e.g., PENDING_TEAM_LEAD, PENDING_QA, PENDING_MANAGER, APPROVED
}