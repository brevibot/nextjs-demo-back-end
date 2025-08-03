package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.Build;
import com.example.dashboardapi.entity.Change;
import com.example.dashboardapi.repository.BuildRepository;
import com.example.dashboardapi.service.BuildService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/builds")
public class BuildController {

    private final BuildRepository buildRepository;
    private final BuildService buildService;


    public BuildController(BuildRepository buildRepository, BuildService buildService) {
        this.buildRepository = buildRepository;
        this.buildService = buildService;
    }

    @GetMapping("/highest-build-number")
    public ResponseEntity<?> getHighestBuildNumber(@RequestParam String branch) {
        Optional<Build> build = buildRepository.findTopByBranchOrderByBuildNumberDesc(branch);
        if (build.isPresent()) {
            return ResponseEntity.ok(Map.of("branch", branch, "highestBuildNumber", build.get().getBuildNumber()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{buildId}/changes-since-last-release")
    public ResponseEntity<List<Change>> getChangesSinceLastRelease(@PathVariable Long buildId) {
        Optional<Build> buildOptional = buildRepository.findById(buildId);
        if (buildOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Change> changes = buildService.getChangesForBuild(buildOptional.get());
        return ResponseEntity.ok(changes);
    }
}