package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Profile("original")
@Slf4j
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configuring ORIGINAL security from course");
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        var userDetailsStudent = User
                .withUsername("radek")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
                .build();

        var userDetailsLinda = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(ApplicationUserRole.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(userDetailsStudent, userDetailsLinda);
    }
}