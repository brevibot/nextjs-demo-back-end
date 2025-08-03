// src/main/java/com/example/dashboardapi/controller/ApprovalController.java

package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.*;
import com.example.dashboardapi.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final BuildRepository buildRepository;
    private final TeamLeadChangeRepository teamLeadChangeRepository;
    private final QaApprovalRepository qaApprovalRepository;
    private final ManagerApprovalRepository managerApprovalRepository;

    public ApprovalController(ApprovalRequestRepository approvalRequestRepository, BuildRepository buildRepository, TeamLeadChangeRepository teamLeadChangeRepository, QaApprovalRepository qaApprovalRepository, ManagerApprovalRepository managerApprovalRepository) {
        this.approvalRequestRepository = approvalRequestRepository;
        this.buildRepository = buildRepository;
        this.teamLeadChangeRepository = teamLeadChangeRepository;
        this.qaApprovalRepository = qaApprovalRepository;
        this.managerApprovalRepository = managerApprovalRepository;
    }

    @PostMapping("/request/{buildId}")
    @Transactional
    public ResponseEntity<?> requestApproval(@PathVariable Long buildId) {
        // Check if an ApprovalRequest already exists for this build
        Optional<ApprovalRequest> existingRequest = approvalRequestRepository.findByBuildId(buildId);
        if (existingRequest.isPresent()) {
            // If it exists, return it instead of creating a new one or erroring
            return ResponseEntity.ok(existingRequest.get());
        }

        Build build = buildRepository.findById(buildId).orElse(null);
        if (build == null) {
            return ResponseEntity.notFound().build();
        }

        ApprovalRequest approvalRequest = new ApprovalRequest();
        approvalRequest.setBuild(build);
        approvalRequest.setStatus("PENDING_TEAM_LEAD");
        approvalRequestRepository.save(approvalRequest);

        return ResponseEntity.ok(approvalRequest);
    }

    @PostMapping("/team-lead/{approvalRequestId}")
    @Transactional
    public ResponseEntity<?> submitTeamLeadChanges(@PathVariable Long approvalRequestId, @RequestBody List<TeamLeadChange> changes) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId).orElse(null);
        if (approvalRequest == null) {
            return ResponseEntity.notFound().build();
        }

        for (TeamLeadChange change : changes) {
            change.setApprovalRequest(approvalRequest);
            teamLeadChangeRepository.save(change);
        }

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
            approvalRequestRepository.save(approvalRequest);
        }

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

            // Get the associated build and set it to approved
            Build build = approvalRequest.getBuild();
            if (build != null) {
                build.setApproved(true);
                buildRepository.save(build);
            }

            approvalRequestRepository.save(approvalRequest);
        }

        return ResponseEntity.ok(approvalRequest);
    }
}