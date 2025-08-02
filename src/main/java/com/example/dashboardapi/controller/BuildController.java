package com.example.dashboardapi.controller;

import com.example.dashboardapi.entity.Build;
import com.example.dashboardapi.repository.BuildRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/builds")
public class BuildController {

    private final BuildRepository buildRepository;

    public BuildController(BuildRepository buildRepository) {
        this.buildRepository = buildRepository;
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
}