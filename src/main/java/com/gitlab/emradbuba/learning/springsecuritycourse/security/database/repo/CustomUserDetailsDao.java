package com.gitlab.emradbuba.learning.springsecuritycourse.security.database.repo;


import com.gitlab.emradbuba.learning.springsecuritycourse.security.database.model.CustomUserDetails;

import java.util.Optional;

public interface CustomUserDetailsDao {
    Optional<CustomUserDetails> findCustomUserDetailsByUsername(String username);
}
