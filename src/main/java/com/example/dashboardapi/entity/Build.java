package com.example.dashboardapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Build {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int buildNumber;
    private int majorVersion;
    private int minorVersion;
    private int patchVersion;
    private String buildStatus;

    @Column(name = "build_date")
    private LocalDateTime date;
    
    private String installLink;
    private String githubActionLink;
    private String sonatypeNexusLink;
    private String branch;
    private boolean isRelease;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "build_id")
    private List<Change> changes = new ArrayList<>();

    public Build(int buildNumber, int major, int minor, int patch, String status, LocalDateTime date, String branch, boolean isRelease) {
        this.buildNumber = buildNumber;
        this.majorVersion = major;
        this.minorVersion = minor;
        this.patchVersion = patch;
        this.buildStatus = status;
        this.date = date;
        this.branch = branch;
        this.isRelease = isRelease;
        this.installLink = "https://example.com/install/" + buildNumber;
        this.githubActionLink = "https://github.com/example/repo/actions/runs/" + buildNumber;
    }
}