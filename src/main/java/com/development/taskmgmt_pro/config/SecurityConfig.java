package com.development.taskmgmt_pro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/projects").hasAnyRole("MANAGER", "PRODUCT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/projects/**").hasAnyRole("MANAGER", "PRODUCT_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/tasks/**").hasAnyRole("MANAGER","PRODUCT_OWNER","DEVELOPER","TESTER")
                        .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasAnyRole("MANAGER","PRODUCT_OWNER")
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .anyRequest().authenticated()         // Other endpoints need auth
                )
                .httpBasic(withDefaults())
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}