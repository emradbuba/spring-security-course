package com.gitlab.emradbuba.learning.springsecuritycourse.security.database;

import com.gitlab.emradbuba.learning.springsecuritycourse.security.database.repo.CustomUserDetailsDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomUserDetailsDao customUserDetailsDao;

    public CustomUserDetailsService(@Qualifier("fake_repo") CustomUserDetailsDao customUserDetailsDao) {
        this.customUserDetailsDao = customUserDetailsDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserDetailsDao
                .findCustomUserDetailsByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' does not exist", username)));
    }
}
