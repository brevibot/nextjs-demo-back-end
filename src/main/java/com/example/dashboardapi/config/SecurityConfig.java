package com.example.dashboardapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

    public SecurityConfig(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) {
        this.relyingPartyRegistrationRepository = relyingPartyRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        DefaultRelyingPartyRegistrationResolver relyingPartyRegistrationResolver =
                new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository);
        Saml2MetadataFilter filter = new Saml2MetadataFilter(
                relyingPartyRegistrationResolver,
                new OpenSamlMetadataResolver());

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(withDefaults())
            .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/login/**", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .saml2Login(withDefaults())
            .saml2Logout(withDefaults())
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}