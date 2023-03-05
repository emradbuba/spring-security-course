package com.gitlab.emradbuba.learning.springsecuritycourse.security.database.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Builder
@Value
public class CustomUserDetails implements UserDetails {
    List<GrantedAuthority> authorities;
    String username;
    String password;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;
}
