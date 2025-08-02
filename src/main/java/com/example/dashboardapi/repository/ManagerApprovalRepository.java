package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.ManagerApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "managerApprovals", path = "manager-approvals")
public interface ManagerApprovalRepository extends JpaRepository<ManagerApproval, Long> {
}