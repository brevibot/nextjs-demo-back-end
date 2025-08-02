package com.example.dashboardapi.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

class BuildTest {

    @Test
    void testBuildEntity() {
        Build build = new Build(1, 1, 0, 0, "SUCCESS", LocalDateTime.now(), "main", true);
        build.setSonatypeNexusLink("http://localhost:8081/repository/maven-releases/");
        assertThat(build.getBuildNumber()).isEqualTo(1);
        assertThat(build.getMajorVersion()).isEqualTo(1);
        assertThat(build.getMinorVersion()).isEqualTo(0);
        assertThat(build.getPatchVersion()).isEqualTo(0);
        assertThat(build.getBuildStatus()).isEqualTo("SUCCESS");
        assertThat(build.getBranch()).isEqualTo("main");
        assertThat(build.isRelease()).isTrue();
        assertThat(build.getSonatypeNexusLink()).isEqualTo("http://localhost:8081/repository/maven-releases/");
    }
}