package com.codecorecix.ecommerce.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

  private static final String SCOPE_WRITE = "SCOPE_write";

  private static final String SCOPE_READ = "SCOPE_read";

  private static final String[] COMMON_PATHS = {"/", "/{id}"};

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/api/users/authorized").permitAll()
                .requestMatchers(HttpMethod.GET, COMMON_PATHS).hasAnyAuthority(SCOPE_READ, SCOPE_WRITE)
                .requestMatchers(HttpMethod.POST, "/").hasAnyAuthority(SCOPE_WRITE)
                .requestMatchers(HttpMethod.PUT, COMMON_PATHS).hasAnyAuthority(SCOPE_WRITE)
                .requestMatchers(HttpMethod.DELETE, COMMON_PATHS).hasAnyAuthority(SCOPE_WRITE)
                .requestMatchers(HttpMethod.PATCH, COMMON_PATHS).hasAnyAuthority(SCOPE_WRITE)
                .anyRequest().authenticated()
        )
        .sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        //.oauth2Login(oauth2Login ->
        //    oauth2Login.loginPage("/oauth/authorization/maintenance-client")
        //)
        .oauth2Login(Customizer.withDefaults())
        .oauth2Client(Customizer.withDefaults())
        .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(Customizer.withDefaults()));
    return http.build();
  }
}
