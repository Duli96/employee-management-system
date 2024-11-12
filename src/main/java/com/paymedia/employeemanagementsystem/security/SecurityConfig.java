package com.paymedia.employeemanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> {
            authorize
                    .requestMatchers(HttpMethod.GET, "/api/**")
                    .hasRole("USER")
                    .requestMatchers("/api/**")
                    .hasRole("ADMIN");
        }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if ("empadmin".equals(username)) {
                return User.withUsername("empadmin")
                        .password(passwordEncoder().encode("exam#123"))
                        .roles("ADMIN")
                        .build();
            } else if ("emp001".equals(username)) {
                return User.withUsername("emp001")
                        .password(passwordEncoder().encode("emppw#123"))
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found");
            }
        };
    }
}
