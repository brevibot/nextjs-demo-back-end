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

        // --- Start of new mock data with changes ---

        Build build127 = new Build(127, 2, 0, 1, "SUCCESS", LocalDateTime.parse("2025-07-18T14:00:00"), "main", false);
        build127.getChanges().add(new Change("u1v2w3x", "Emily White", "fix: Dashboard data not refreshing automatically"));

        Build build128 = new Build(128, 1, 2, 0, "SUCCESS", LocalDateTime.parse("2025-07-18T15:00:00"), "feature/new-login", false);
        build128.getChanges().addAll(List.of(
            new Change("y4z5a6b", "Chris Green", "feat: Add password reset functionality"),
            new Change("c7d8e9f", "Chris Green", "style: Update login page UI")
        ));

        Build build129 = new Build(129, 2, 0, 2, "FAILURE", LocalDateTime.parse("2025-07-19T10:00:00"), "main", false);
        build129.getChanges().add(new Change("g1h2i3j", "John Smith", "fix: API endpoint for reports returns 500 error"));

        Build build130 = new Build(130, 2, 1, 0, "SUCCESS", LocalDateTime.parse("2025-07-20T11:30:00"), "release/v2.1", true);
        build130.getChanges().add(new Change("k4l5m6n", "Jane Doe", "build: Prepare for release v2.1.0"));

        Build build131 = new Build(131, 2, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-21T09:00:00"), "main", false);
        build131.getChanges().add(new Change("o7p8q9r", "Emily White", "docs: Update README with new API instructions"));

        Build build132 = new Build(132, 1, 2, 1, "SUCCESS", LocalDateTime.parse("2025-07-22T16:00:00"), "feature/new-login", false);
        build132.getChanges().add(new Change("s1t2u3v", "Chris Green", "fix: Password reset token expires too quickly"));

        Build build133 = new Build(133, 2, 1, 2, "IN_PROGRESS", LocalDateTime.parse("2025-07-23T10:00:00"), "main", false);
        build133.getChanges().add(new Change("w4x5y6z", "John Smith", "refactor: Improve performance of data import process"));

        Build build134 = new Build(134, 3, 0, 0, "SUCCESS", LocalDateTime.parse("2025-07-24T08:00:00"), "main", true);
        build134.getChanges().addAll(List.of(
            new Change("a1b3c5d", "Jane Doe", "feat: Introduce calendar view for events"),
            new Change("e6f7g8h", "Jane Doe", "test: Add integration tests for calendar API")
        ));

        Build build135 = new Build(135, 3, 0, 1, "SUCCESS", LocalDateTime.parse("2025-07-25T13:00:00"), "development", false);
        build135.getChanges().add(new Change("i9j1k2l", "Michael Brown", "feat: Add webhook support for build notifications"));

        Build build136 = new Build(136, 1, 3, 0, "SUCCESS", LocalDateTime.parse("2025-07-26T11:00:00"), "feature/analytics", false);
        build136.getChanges().add(new Change("m3n4o5p", "Sarah Miller", "feat: Initial commit for analytics module"));

        Build build137 = new Build(137, 3, 0, 2, "FAILURE", LocalDateTime.parse("2025-07-27T15:00:00"), "main", false);
        build137.getChanges().add(new Change("q6r7s8t", "Emily White", "fix: Calendar events are not showing for all users"));

        Build build138 = new Build(138, 3, 1, 0, "SUCCESS", LocalDateTime.parse("2025-07-28T10:00:00"), "release/v3.1", true);
        build138.getChanges().add(new Change("u9v1w2x", "Jane Doe", "build: Prepare for release v3.1.0"));

        Build build139 = new Build(139, 3, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-29T14:30:00"), "main", false);
        build139.getChanges().add(new Change("y3z4a5b", "John Smith", "perf: Optimize database queries on main dashboard"));
        
        Build build140 = new Build(140, 1, 3, 1, "SUCCESS", LocalDateTime.parse("2025-07-30T16:00:00"), "feature/analytics", false);
        build140.getChanges().addAll(List.of(
            new Change("c6d7e8f", "Sarah Miller", "feat: Add chart for build status over time"),
            new Change("g9h1i2j", "Sarah Miller", "refactor: Analytics data fetching logic")
        ));
        
        Build build141 = new Build(141, 3, 1, 2, "SUCCESS", LocalDateTime.parse("2025-07-31T09:00:00"), "main", false);
        build141.getChanges().add(new Change("k3l4m5n", "Emily White", "fix: Notification banner is not dismissible"));

        Build build142 = new Build(142, 4, 0, 0, "SUCCESS", LocalDateTime.parse("2025-08-01T11:00:00"), "main", true);
        build142.getChanges().add(new Change("o6p7q8r", "Jane Doe", "feat: Admin panel for managing notifications"));

        Build build143 = new Build(143, 4, 0, 1, "IN_PROGRESS", LocalDateTime.parse("2025-08-02T08:00:00"), "development", false);
        build143.getChanges().add(new Change("s9t1u2v", "Michael Brown", "feat: Allow admins to clear active notification"));

        Build build144 = new Build(144, 2, 2, 0, "SUCCESS", LocalDateTime.parse("2025-08-01T18:00:00"), "hotfix/login-bug", false);
        build144.getChanges().add(new Change("w3x4y5z", "Chris Green", "fix: Critical login vulnerability (CVE-2025-12345)"));

        Build build145 = new Build(145, 2, 2, 1, "SUCCESS", LocalDateTime.parse("2025-08-02T09:00:00"), "main", false);
        build145.getChanges().add(new Change("a1b2c3d4", "Jane Doe", "merge: Merge hotfix/login-bug into main"));

        Build build146 = new Build(146, 4, 1, 0, "SUCCESS", LocalDateTime.parse("2025-08-03T10:00:00"), "release/v4.1", true);
        build146.getChanges().add(new Change("e5f6g7h8", "Jane Doe", "build: Prepare for release v4.1.0"));

        List<Build> newBuilds = List.of(
            build127, build128, build129, build130, build131, build132, build133,
            build134, build135, build136, build137, build138, build139, build140,
            build141, build142, build143, build144, build145, build146
        );

        // Combine the original and new builds
        List<Build> allBuilds = new java.util.ArrayList<>();
        allBuilds.addAll(List.of(build123, build124, build125, build126));
        allBuilds.addAll(newBuilds);

        buildRepository.saveAll(allBuilds);
    }

    private void loadEventData() {
        // Using the current date to make events more relevant
        ZonedDateTime now = ZonedDateTime.now();
        eventRepository.saveAll(List.of(
            new Event("Project Alpha Deadline", now.plusDays(8), now.plusDays(9), true, "#dc3545"),
            new Event("Team Sync", now.plusDays(13).withHour(10).withMinute(30), now.plusDays(13).withHour(11).withMinute(30), false, "#0d6efd"),
            new Event("Deployment Window", now.plusDays(18).withHour(14), now.plusDays(18).withHour(16), false, "#198754")
        ));
    }
}