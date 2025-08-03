package com.example.dashboardapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Saml2MetadataFilter filter = new Saml2MetadataFilter(relyingPartyRegistrationResolver, new OpenSamlMetadataResolver());

        http
            // 1. Disable CSRF protection for stateless REST APIs
            .csrf(csrf -> csrf.disable())
            
            // 2. Configure CORS to use the settings from another configuration class (like RestConfig)
            .cors(withDefaults())
            
            // 3. Configure authorization rules
            .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/api/approvals/deployer/**").hasRole("DEPLOYER")
                // .requestMatchers("/api/approvals/team-lead/**").hasRole("TEAM_LEAD")
                // .requestMatchers("/api/approvals/qa/**").hasRole("QA")
                // .requestMatchers("/api/approvals/manager/**").hasRole("MANAGER")
                .anyRequest()
                .authenticated())
            .saml2Login(withDefaults())
            .saml2Logout(withDefaults())
            .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}