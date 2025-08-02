package com.example.dashboardapi.repository;

import com.example.dashboardapi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "events", path = "events")
public interface EventRepository extends JpaRepository<Event, Long> {}