package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Build;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "builds", path = "builds")
public interface BuildRepository extends JpaRepository<Build, Long> {
    Optional<Build> findTopByBranchOrderByBuildNumberDesc(String branch);

    // Expose the findByBranch method under the /api/builds/search/findByBranch endpoint
    @RestResource(path = "findByBranch", rel = "findByBranch")
    Page<Build> findByBranch(@Param("branch") String branch, Pageable pageable);
}