package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "approvalRequests", path = "approval-requests")
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
}