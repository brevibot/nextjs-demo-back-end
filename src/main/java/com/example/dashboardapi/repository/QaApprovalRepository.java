package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.QaApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "qaApprovals", path = "qa-approvals")
public interface QaApprovalRepository extends JpaRepository<QaApproval, Long> {
}