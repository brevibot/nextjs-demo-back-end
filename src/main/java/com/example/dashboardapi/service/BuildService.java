package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.Build;
import com.example.dashboardapi.entity.Change;
import com.example.dashboardapi.repository.BuildRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildService {

    private final BuildRepository buildRepository;

    public BuildService(BuildRepository buildRepository) {
        this.buildRepository = buildRepository;
    }

    public List<Change> getChangesForBuild(Build currentBuild) {
        Optional<Build> lastReleaseBuildOpt = buildRepository.findTopByIsReleaseTrueAndDateBeforeOrderByDateDesc(currentBuild.getDate());

        LocalDateTime startDate = lastReleaseBuildOpt.map(Build::getDate).orElse(LocalDateTime.MIN);

        List<Build> buildsSinceLastRelease = buildRepository.findAll().stream()
                .filter(b -> b.getDate().isAfter(startDate) && b.getDate().isBefore(currentBuild.getDate()))
                .collect(Collectors.toList());
        buildsSinceLastRelease.add(currentBuild);


        return buildsSinceLastRelease.stream()
                .flatMap(b -> b.getChanges().stream())
                .sorted(Comparator.comparing(Change::getId)) // Assuming Change has a date or sequential ID
                .collect(Collectors.toList());
    }
}