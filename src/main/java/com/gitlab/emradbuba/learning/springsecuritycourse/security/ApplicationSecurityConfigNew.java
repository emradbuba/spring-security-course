package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("custom")
@Slf4j
public class ApplicationSecurityConfigNew {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfigNew(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    // Newer approach creating a bean (since SpringSecurity 5.7.0-M2)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configuring CUSTOM security from course");
        return httpSecurity.authorizeRequests((auth) ->
                                auth.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetails = User
                .withUsername("radek")
                .password(passwordEncoder.encode("password"))
                .roles("STUDENT") // ROLE_STUDENT
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }
}
