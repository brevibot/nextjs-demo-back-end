package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.DeployerApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "deployerApprovals", path = "deployer-approvals")
public interface DeployerApprovalRepository extends JpaRepository<DeployerApproval, Long> {
}