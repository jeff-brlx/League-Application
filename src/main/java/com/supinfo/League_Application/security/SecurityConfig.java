package com.supinfo.League_Application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll() // Autoriser toutes les requêtes sans authentification
                )
                .csrf().disable() // Désactiver CSRF pour permettre les requêtes POST sans authentification
                .formLogin().disable() // Désactiver le formulaire de login
                .httpBasic().disable(); // Désactiver l'authentification HTTP basique
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("journalist")
                .password(passwordEncoder().encode("password"))
                .roles("JOURNALIST")
                .build());
        userDetailsManager.createUser(User.withUsername("member-league")
                .password(passwordEncoder().encode("password"))
                .roles("MEMBER_LEAGUE")
                .build());
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
