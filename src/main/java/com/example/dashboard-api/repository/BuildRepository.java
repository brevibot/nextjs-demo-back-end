package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "builds", path = "builds")
public interface BuildRepository extends JpaRepository<Build, Long> {}