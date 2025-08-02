package com.example.dashboardapi.util;

import com.example.dashboardapi.entity.Build;
import com.example.dashboardapi.entity.Change;
import com.example.dashboardapi.entity.Event;
import com.example.dashboardapi.repository.BuildRepository;
import com.example.dashboardapi.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final BuildRepository buildRepository;
    private final EventRepository eventRepository;
    private final Environment environment;

    public DataLoader(BuildRepository buildRepository, EventRepository eventRepository, Environment environment) {
        this.buildRepository = buildRepository;
        this.eventRepository = eventRepository;
        this.environment = environment;
    }

    @Override
    public void run(String... args) throws Exception {
        if (isLocalProfile()) {
            loadBuildData();
            loadEventData();
        }
    }

    private boolean isLocalProfile() {
        for (String profile : environment.getActiveProfiles()) {
            if ("local".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return false;
    }

    private void loadBuildData() {
        Build build123 = new Build(123, 1, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-15T13:00:00"), "development", false);
        build123.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/com/example/dashboard-api/1.1.1/");
        build123.getChanges().addAll(List.of(
            new Change("a1b2c3d", "Jane Doe", "feat: Add user authentication endpoint"),
            new Change("e4f5g6h", "John Smith", "fix: Correct calculation in payment module")
        ));

        Build build124 = new Build(124, 1, 1, 2, "SUCCESS", LocalDateTime.parse("2025-07-16T10:00:00"), "development", false);
        build124.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/com/example/dashboard-api/1.1.2/");
        build124.getChanges().add(new Change("i7j8k9l", "Jane Doe", "refactor: Simplify database query logic"));

        Build build125 = new Build(125, 1, 1, 0, "FAILURE", LocalDateTime.parse("2025-07-16T11:00:00"), "main", false);
        build125.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/com/example/dashboard-api/1.1.0/");
        build125.getChanges().add(new Change("m1n2o3p", "Jane Doe", "chore: Update dependencies"));

        Build build126 = new Build(126, 2, 0, 0, "SUCCESS", LocalDateTime.parse("2025-07-17T09:30:00"), "main", true);
        build126.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/com/example/dashboard-api/2.0.0/");
        build126.getChanges().add(new Change("q4r5s6t", "John Smith", "feat: Implement new reporting dashboard"));

        List<Build> newBuilds = List.of(
            new Build(127, 2, 0, 1, "SUCCESS", LocalDateTime.parse("2025-07-18T14:00:00"), "main", false),
            new Build(128, 1, 2, 0, "SUCCESS", LocalDateTime.parse("2025-07-18T15:00:00"), "feature/new-login", false),
            new Build(129, 2, 0, 2, "FAILURE", LocalDateTime.parse("2025-07-19T10:00:00"), "main", false),
            new Build(130, 2, 1, 0, "SUCCESS", LocalDateTime.parse("2025-07-20T11:30:00"), "release/v2.1", true),
            new Build(131, 2, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-21T09:00:00"), "main", false),
            new Build(132, 1, 2, 1, "SUCCESS", LocalDateTime.parse("2025-07-22T16:00:00"), "feature/new-login", false),
            new Build(133, 2, 1, 2, "IN_PROGRESS", LocalDateTime.parse("2025-07-23T10:00:00"), "main", false),
            new Build(134, 3, 0, 0, "SUCCESS", LocalDateTime.parse("2025-07-24T08:00:00"), "main", true),
            new Build(135, 3, 0, 1, "SUCCESS", LocalDateTime.parse("2025-07-25T13:00:00"), "development", false),
            new Build(136, 1, 3, 0, "SUCCESS", LocalDateTime.parse("2025-07-26T11:00:00"), "feature/analytics", false),
            new Build(137, 3, 0, 2, "FAILURE", LocalDateTime.parse("2025-07-27T15:00:00"), "main", false),
            new Build(138, 3, 1, 0, "SUCCESS", LocalDateTime.parse("2025-07-28T10:00:00"), "release/v3.1", true),
            new Build(139, 3, 1, 1, "SUCCESS", LocalDateTime.parse("2025-07-29T14:30:00"), "main", false),
            new Build(140, 1, 3, 1, "SUCCESS", LocalDateTime.parse("2025-07-30T16:00:00"), "feature/analytics", false),
            new Build(141, 3, 1, 2, "SUCCESS", LocalDateTime.parse("2025-07-31T09:00:00"), "main", false),
            new Build(142, 4, 0, 0, "SUCCESS", LocalDateTime.parse("2025-08-01T11:00:00"), "main", true),
            new Build(143, 4, 0, 1, "IN_PROGRESS", LocalDateTime.parse("2025-08-02T08:00:00"), "development", false),
            new Build(144, 2, 2, 0, "SUCCESS", LocalDateTime.parse("2025-08-01T18:00:00"), "hotfix/login-bug", false),
            new Build(145, 2, 2, 1, "SUCCESS", LocalDateTime.parse("2025-08-02T09:00:00"), "main", false),
            new Build(146, 4, 1, 0, "SUCCESS", LocalDateTime.parse("2025-08-03T10:00:00"), "release/v4.1", true)
        );

        newBuilds.forEach(build -> build.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/com/example/dashboard-api/" + build.getMajorVersion() + "." + build.getMinorVersion() + "." + build.getPatchVersion() + "/"));

        List<Build> allBuilds = new java.util.ArrayList<>();
        allBuilds.addAll(List.of(build123, build124, build125, build126));
        allBuilds.addAll(newBuilds);

        buildRepository.saveAll(allBuilds);
    }

    private void loadEventData() {
        eventRepository.saveAll(List.of(
            new Event("Project Alpha Deadline", ZonedDateTime.parse("2025-08-10T00:00:00Z"), ZonedDateTime.parse("2025-08-11T00:00:00Z"), true, "#dc3545"),
            new Event("Team Sync", ZonedDateTime.parse("2025-08-15T10:30:00Z"), ZonedDateTime.parse("2025-08-15T11:30:00Z"), false, "#0d6efd"),
            new Event("Deployment Window", ZonedDateTime.parse("2025-08-20T14:00:00Z"), ZonedDateTime.parse("2025-08-20T16:00:00Z"), false, "#198754")
        ));
    }
}