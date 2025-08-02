package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Change;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Exposing this is optional, but can be useful for direct queries.
// By default, changes will be accessible via the Build entity.
@RepositoryRestResource(collectionResourceRel = "changes", path = "changes")
public interface ChangeRepository extends JpaRepository<Change, Long> {}