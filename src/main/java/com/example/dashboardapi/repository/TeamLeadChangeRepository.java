package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.TeamLeadChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "teamLeadChanges", path = "team-lead-changes")
public interface TeamLeadChangeRepository extends JpaRepository<TeamLeadChange, Long> {
}