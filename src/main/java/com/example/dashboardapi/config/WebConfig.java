package com.example.dashboardapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // The Okta domain is extracted from your metadata file
                String oktaDomain = "https://dev-77801819.okta.com";

                // This global configuration applies to all endpoints
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000", oktaDomain)
                        .allowedMethods("*") // Allows GET, POST, PUT, DELETE, etc.
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}