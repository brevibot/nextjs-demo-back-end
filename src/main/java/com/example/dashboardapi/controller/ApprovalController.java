package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.*;
import com.example.dashboardapi.repository.*;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final BuildRepository buildRepository;
    private final TeamLeadChangeRepository teamLeadChangeRepository;
    private final QaApprovalRepository qaApprovalRepository;
    private final ManagerApprovalRepository managerApprovalRepository;
    private final DeployerApprovalRepository deployerApprovalRepository;
    private final TeamLeadApprovalRepository teamLeadApprovalRepository;

    // Helper class for the Team Lead submission
    @Data
    static class TeamLeadSubmissionRequest {
        private String approvedBy;
        private List<TeamLeadChange> changes;
    }

    public ApprovalController(ApprovalRequestRepository approvalRequestRepository, BuildRepository buildRepository, TeamLeadChangeRepository teamLeadChangeRepository, QaApprovalRepository qaApprovalRepository, ManagerApprovalRepository managerApprovalRepository, DeployerApprovalRepository deployerApprovalRepository, TeamLeadApprovalRepository teamLeadApprovalRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.buildRepository = buildRepository;
        this.teamLeadChangeRepository = teamLeadChangeRepository;
        this.qaApprovalRepository = qaApprovalRepository;
        this.managerApprovalRepository = managerApprovalRepository;
        this.deployerApprovalRepository = deployerApprovalRepository;
        this.teamLeadApprovalRepository = teamLeadApprovalRepository;
    }

    @PostMapping("/request/{buildId}")
    public ResponseEntity<?> requestApproval(@PathVariable Long buildId) {
        try {
            Build build = buildRepository.findById(buildId).orElse(null);
            if (build == null) {
                return ResponseEntity.notFound().build();
            }
            ApprovalRequest approvalRequest = new ApprovalRequest();
            approvalRequest.setBuild(build);
            approvalRequest.setStatus("PENDING_DEPLOYER");
            approvalRequestRepository.saveAndFlush(approvalRequest);
            return ResponseEntity.ok(approvalRequest);
        } catch (DataIntegrityViolationException e) {
            Optional<ApprovalRequest> existingRequest = approvalRequestRepository.findByBuildId(buildId);
            if (existingRequest.isPresent()) {
                return ResponseEntity.ok(existingRequest.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body(Map.of("error", "Could not create or find approval request after data integrity error."));
            }
        }
    }

    @PostMapping("/deployer/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> submitDeployerApproval(@PathVariable Long approvalRequestId, @RequestBody DeployerApproval deployerApproval) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }
        if (!"PENDING_DEPLOYER".equals(approvalRequest.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Request is not awaiting deployer approval."));
        }

        deployerApproval.setApprovalRequest(approvalRequest);
        deployerApproval.setApprovalDate(LocalDateTime.now());
        deployerApprovalRepository.save(deployerApproval);

        if (deployerApproval.isApproved()) {
            approvalRequest.setStatus("PENDING_TEAM_LEAD");
        } else {
            approvalRequest.setStatus("CANCELED");
        }
        approvalRequestRepository.save(approvalRequest);

        return ResponseEntity.ok(approvalRequest);
    }

    @PostMapping("/team-lead/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> submitTeamLeadChanges(@PathVariable Long approvalRequestId, @RequestBody TeamLeadSubmissionRequest request) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Save each change
        for (TeamLeadChange change : request.getChanges()) {
            change.setApprovalRequest(approvalRequest);
            change.setSubmittedBy(request.getApprovedBy());
            teamLeadChangeRepository.save(change);
        }

        // Create the approval record
        TeamLeadApproval teamLeadApproval = new TeamLeadApproval();
        teamLeadApproval.setApprovedBy(request.getApprovedBy());
        teamLeadApproval.setApproved(true);
        teamLeadApproval.setApprovalDate(LocalDateTime.now());
        teamLeadApproval.setApprovalRequest(approvalRequest);
        teamLeadApprovalRepository.save(teamLeadApproval);
        
        approvalRequest.setStatus("PENDING_QA");
        approvalRequestRepository.save(approvalRequest);

        return ResponseEntity.ok(approvalRequest);
    }

    @PostMapping("/qa/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> submitQaApproval(@PathVariable Long approvalRequestId, @RequestBody QaApproval qaApproval) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }
        qaApproval.setApprovalRequest(approvalRequest);
        qaApproval.setApprovalDate(LocalDateTime.now());
        qaApprovalRepository.save(qaApproval);
        if (qaApproval.isApproved()) {
            approvalRequest.setStatus("PENDING_MANAGER");
        } else {
            approvalRequest.setStatus("CANCELED");
        }
        approvalRequestRepository.save(approvalRequest);
        return ResponseEntity.ok(approvalRequest);
    }

    @PostMapping("/manager/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> submitManagerApproval(@PathVariable Long approvalRequestId, @RequestBody ManagerApproval managerApproval) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }
        managerApproval.setApprovalRequest(approvalRequest);
        managerApproval.setApprovalDate(LocalDateTime.now());
        managerApprovalRepository.save(managerApproval);
        if (managerApproval.isApproved()) {
            approvalRequest.setStatus("APPROVED");
            Build build = approvalRequest.getBuild();
            if (build != null) {
                build.setApproved(true);
                buildRepository.save(build);
            }
        } else {
            approvalRequest.setStatus("CANCELED");
        }
        approvalRequestRepository.save(approvalRequest);
        return ResponseEntity.ok(approvalRequest);
    }

    @PostMapping("/reset/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> resetApproval(@PathVariable Long approvalRequestId) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }

        if (!"CANCELED".equals(approvalRequest.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Only canceled requests can be reset."));
        }

        // Clear previous approval/change records to start fresh
        approvalRequest.getDeployerApprovals().clear();
        approvalRequest.getTeamLeadApprovals().clear();
        approvalRequest.getTeamLeadChanges().clear();
        approvalRequest.getQaApprovals().clear();
        approvalRequest.getManagerApprovals().clear();

        // Reset the status
        approvalRequest.setStatus("PENDING_DEPLOYER");

        // Save the updated request
        ApprovalRequest updatedRequest = approvalRequestRepository.save(approvalRequest);

        return ResponseEntity.ok(updatedRequest);
    }
    
    @PostMapping("/cancel/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> cancelApproval(@PathVariable Long approvalRequestId) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }

        String currentStatus = approvalRequest.getStatus();
        if ("APPROVED".equals(currentStatus) || "CANCELED".equals(currentStatus)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Request is already in a closed state."));
        }

        approvalRequest.setStatus("CANCELED");
        approvalRequestRepository.save(approvalRequest);

        return ResponseEntity.ok(approvalRequest);
    }
}