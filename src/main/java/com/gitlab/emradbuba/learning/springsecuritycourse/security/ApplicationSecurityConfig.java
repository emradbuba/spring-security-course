package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import com.gitlab.emradbuba.learning.springsecuritycourse.security.authorities.ApplicationUserPermission;
import com.gitlab.emradbuba.learning.springsecuritycourse.security.authorities.ApplicationUserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    // Newer approach creating a bean (since SpringSecurity 5.7.0-M2)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configuring CUSTOM security from course");
        return httpSecurity
                .csrf().disable()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests((auth) ->
                        auth.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                                .antMatchers("/api/v1/unsecured/**").permitAll()
                                //
                                //.antMatchers("/api/v1/students/**").hasAnyRole(ApplicationUserRole.STUDENT.name(), ApplicationUserRole.JUNIOR_ADMIN.name(), ApplicationUserRole.MAIN_ADMIN.name())
                                .antMatchers("/api/v1/students/**").hasAuthority(ApplicationUserPermission.CAN_READ_BASIC_CONTENT.getPermissionName())
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
                //.httpBasic(Customizer.withDefaults())
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/afterLogin", true)
                .and().rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21L))
                    .key("the-remember-me-key")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login")
                .and().build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        var guest = User
//                .withUsername("guest")
//                .password(passwordEncoder.encode("guest123"))
//                .roles("GUEST")
//                .build();
//        var studentDetails = User
//                .withUsername("student")
//                .password(passwordEncoder.encode("student123"))
//                //.roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
//                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//                .build();
//        var juniorAdminDetails = User
//                .withUsername("junioradmin")
//                .password(passwordEncoder.encode("admin123"))
//                //.roles(ApplicationUserRole.JUNIOR_ADMIN.name()) // ROLE_JUNIOR_ADMIN
//                .authorities(ApplicationUserRole.JUNIOR_ADMIN.getGrantedAuthorities())
//                .build();
//        var adminDetails = User
//                .withUsername("admin")
//                .password(passwordEncoder.encode("admin123"))
//                //.roles(ApplicationUserRole.MAIN_ADMIN.name()) // ROLE_MAIN_ADMIN
//                .authorities(ApplicationUserRole.MAIN_ADMIN.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(guest, studentDetails, juniorAdminDetails, adminDetails);
//    }
}
