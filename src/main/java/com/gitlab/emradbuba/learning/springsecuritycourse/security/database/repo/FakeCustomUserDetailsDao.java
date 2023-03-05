package com.gitlab.emradbuba.learning.springsecuritycourse.security.database.repo;

import com.gitlab.emradbuba.learning.springsecuritycourse.security.ApplicationUserRole;
import com.gitlab.emradbuba.learning.springsecuritycourse.security.database.model.CustomUserDetails;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class FakeCustomUserDetailsDao implements CustomUserDetailsDao {
    private final PasswordEncoder passwordEncoder;
    private Map<String, CustomUserDetails> FAKE_DATABASE = new HashMap<>();

    @Override
    public Optional<CustomUserDetails> findCustomUserDetailsByUsername(String username) {
        return Optional.ofNullable(FAKE_DATABASE.get(username));
    }


    public FakeCustomUserDetailsDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        FAKE_DATABASE = Lists.newArrayList(
                        CustomUserDetails.builder()
                                .username("guest")
                                .password(passwordEncoder.encode("guest123"))
                                .authorities(Collections.emptyList())
                                .isEnabled(true)
                                .isAccountNonLocked(true)
                                .isCredentialsNonExpired(true)
                                .build(),
                        CustomUserDetails.builder()
                                .username("student")
                                .password(passwordEncoder.encode("student123"))
                                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities().stream().toList())
                                .isEnabled(true)
                                .isAccountNonLocked(true)
                                .isCredentialsNonExpired(true)
                                .build(),
                        CustomUserDetails.builder()
                                .username("junioradmin")
                                .password(passwordEncoder.encode("admin123"))
                                .authorities(ApplicationUserRole.JUNIOR_ADMIN.getGrantedAuthorities().stream().toList())
                                .isEnabled(true)
                                .isAccountNonLocked(true)
                                .isCredentialsNonExpired(true)
                                .build(),
                        CustomUserDetails.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin123"))
                                .authorities(ApplicationUserRole.MAIN_ADMIN.getGrantedAuthorities().stream().toList())
                                .isEnabled(true)
                                .isAccountNonLocked(true)
                                .isCredentialsNonExpired(true)
                                .build()
                ).stream()
                .collect(Collectors.toMap(
                        CustomUserDetails::getUsername, Function.identity())
                );
    }
}
