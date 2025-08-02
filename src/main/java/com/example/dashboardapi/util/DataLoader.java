package com.example.dashboardapi.util;

import com.example.dashboardapi.entity.Build;
import com.example.dashboardapi.entity.Change;
import com.example.dashboardapi.entity.Event;
import com.example.dashboardapi.repository.BuildRepository;
import com.example.dashboardapi.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

// This component runs on startup and populates the H2 database with mock data.
@Component
public class DataLoader implements CommandLineRunner {

    private final BuildRepository buildRepository;
    private final EventRepository eventRepository;

    public DataLoader(BuildRepository buildRepository, EventRepository eventRepository) {
        this.buildRepository = buildRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBuildData();
        loadEventData();
    }

    private void loadBuildData() {
        Build build123 = new Build(123, 1, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-15T13:00:00"), "development", false);
        build123.getChanges().addAll(List.of(
            new Change("a1b2c3d", "Jane Doe", "feat: Add user authentication endpoint"),
            new Change("e4f5g6h", "John Smith", "fix: Correct calculation in payment module")
        ));

        Build build124 = new Build(124, 1, 1, 2, "SUCCESS", LocalDateTime.parse("2025-07-16T10:00:00"), "development", false);
        build124.getChanges().add(new Change("i7j8k9l", "Jane Doe", "refactor: Simplify database query logic"));

        Build build125 = new Build(125, 1, 1, 0, "FAILURE", LocalDateTime.parse("2025-07-16T11:00:00"), "main", false);
        build125.getChanges().add(new Change("m1n2o3p", "Jane Doe", "chore: Update dependencies"));


        Build build126 = new Build(126, 2, 0, 0, "SUCCESS", LocalDateTime.parse("2025-07-17T09:30:00"), "main", true);
        build126.getChanges().add(new Change("q4r5s6t", "John Smith", "feat: Implement new reporting dashboard"));


        buildRepository.saveAll(List.of(build123, build124, build125, build126));
    }

    private void loadEventData() {
        eventRepository.saveAll(List.of(
            new Event("Project Alpha Deadline", ZonedDateTime.parse("2025-08-10T00:00:00Z"), ZonedDateTime.parse("2025-08-11T00:00:00Z"), true, "#dc3545"),
            new Event("Team Sync", ZonedDateTime.parse("2025-08-15T10:30:00Z"), ZonedDateTime.parse("2025-08-15T11:30:00Z"), false, "#0d6efd"),
            new Event("Deployment Window", ZonedDateTime.parse("2025-08-20T14:00:00Z"), ZonedDateTime.parse("2025-08-20T16:00:00Z"), false, "#198754")
        ));
    }
}