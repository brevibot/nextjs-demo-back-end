package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.TeamLeadApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamLeadApprovals", path = "team-lead-approvals")
public interface TeamLeadApprovalRepository extends JpaRepository<TeamLeadApproval, Long> {
}