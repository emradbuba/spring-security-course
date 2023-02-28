package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.gitlab.emradbuba.learning.springsecuritycourse.security.ApplicationUserRole.*;

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
        return httpSecurity
                .csrf().disable()
                .authorizeRequests((auth) ->
                        auth.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                                .antMatchers("/api/**").hasRole(STUDENT.name())
                                .antMatchers(HttpMethod.DELETE, "management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE_PERM.name())
                                .antMatchers(HttpMethod.POST, "management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE_PERM.name())
                                .antMatchers(HttpMethod.PUT, "management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE_PERM.name())
                                .antMatchers(HttpMethod.GET, "management/api/**").hasAnyRole(ADMIN.name(), ADMIN_JUNIOR.name())
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var studentDetails = User
                .withUsername("student")
                .password(passwordEncoder.encode("student123"))
                //.roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();
        var adminDetails = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .authorities(ADMIN.getGrantedAuthorities())// ROLE_ADMIN
                .build();
        var adminJunior = User
                .withUsername("junioradmin")
                .password(passwordEncoder.encode("junioradmin123"))
                //.roles(ApplicationUserRole.ADMIN_JUNIOR.name())
                .authorities(ADMIN_JUNIOR.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(studentDetails, adminDetails, adminJunior);
    }
}
