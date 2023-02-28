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
                                .antMatchers("/api/v1/students/**").hasAnyRole(ApplicationUserRole.STUDENT.name(), ApplicationUserRole.JUNIOR_ADMIN.name(), ApplicationUserRole.MAIN_ADMIN.name())
                                //.antMatchers("/api/v1/students/**").hasAuthority(ApplicationUserPermission.PERMISSION_STUDENT_BASIC_CONTENT_READ.getPermissionName())
                                //
                                .antMatchers(HttpMethod.DELETE, "/management/**").hasRole(ApplicationUserRole.MAIN_ADMIN.name())
                                //.antMatchers(HttpMethod.DELETE, "management/**").hasAuthority(ApplicationUserPermission.PERMISSION_ADMIN_CONTENT_CREATE_OR_UPDATE.name())
                                //
                                .antMatchers(HttpMethod.POST, "/management/**").hasAnyRole(ApplicationUserRole.MAIN_ADMIN.name(), ApplicationUserRole.JUNIOR_ADMIN.name())
                                //.antMatchers(HttpMethod.POST, "management/**").hasAuthority(ApplicationUserPermission.PERMISSION_ADMIN_CONTENT_CREATE_OR_UPDATE.name())
                                //
                                .antMatchers(HttpMethod.PUT, "/management/**").hasAnyRole(ApplicationUserRole.MAIN_ADMIN.name(), ApplicationUserRole.JUNIOR_ADMIN.name())
                                //.antMatchers(HttpMethod.PUT, "management/**").hasAuthority(ApplicationUserPermission.PERMISSION_ADMIN_CONTENT_CREATE_OR_UPDATE.name())
                                //
                                .antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(ApplicationUserRole.MAIN_ADMIN.name(), ApplicationUserRole.JUNIOR_ADMIN.name())
                                //.antMatchers(HttpMethod.GET, "management/**").hasAnyRole(MAIN_ADMIN.name(), JUNIOR_ADMIN.name())
                                //
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var guest = User
                .withUsername("guest")
                .password(passwordEncoder.encode("guest123"))
                .roles("GUEST")
                .build();
        var studentDetails = User
                .withUsername("student")
                .password(passwordEncoder.encode("student123"))
                .roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
                //.authorities(STUDENT.getGrantedAuthorities())
                .build();
        var juniorAdminDetails = User
                .withUsername("junioradmin")
                .password(passwordEncoder.encode("junioradmin123"))
                .roles(ApplicationUserRole.JUNIOR_ADMIN.name()) // ROLE_JUNIOR_ADMIN
                //.authorities(MAIN_ADMIN.getGrantedAuthorities())// ROLE_ADMIN
                .build();
        var adminDetails = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles(ApplicationUserRole.MAIN_ADMIN.name())
                //.authorities(JUNIOR_ADMIN.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(guest, studentDetails, juniorAdminDetails, adminDetails);
    }
}
