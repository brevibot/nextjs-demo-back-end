package com.example.dashboardapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF protection for stateless REST APIs
            .csrf(csrf -> csrf.disable())
            
            // 2. Configure CORS to use the settings from another configuration class (like RestConfig)
            .cors(withDefaults())
            
            // 3. Configure authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/approvals/team-lead/**").hasRole("TEAM_LEAD")
                .requestMatchers("/api/approvals/qa/**").hasRole("QA")
                .requestMatchers("/api/approvals/manager/**").hasRole("MANAGER")
                .anyRequest().permitAll()
            );

        return http.build();
    }
}