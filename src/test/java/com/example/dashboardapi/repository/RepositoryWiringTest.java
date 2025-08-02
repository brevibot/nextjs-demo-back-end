package com.example.dashboardapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepositoryWiringTest {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChangeRepository changeRepository;

    @Test
    void repositoriesAreInjected() {
        assertThat(buildRepository).isNotNull();
        assertThat(eventRepository).isNotNull();
        assertThat(notificationRepository).isNotNull();
        assertThat(changeRepository).isNotNull();
    }
}