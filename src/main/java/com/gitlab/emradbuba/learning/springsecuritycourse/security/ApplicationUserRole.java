package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.gitlab.emradbuba.learning.springsecuritycourse.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(PERMISSION_STUDENT_BASIC_CONTENT_READ)),
    JUNIOR_ADMIN(Sets.newHashSet(PERMISSION_STUDENT_BASIC_CONTENT_READ, PERMISSION_STUDENT_ALL_CONTENT_READ,
            PERMISSION_STUDENT_CONTENT_CREATE_OR_UPDATE)),
    MAIN_ADMIN(Sets.newHashSet(PERMISSION_STUDENT_BASIC_CONTENT_READ, PERMISSION_STUDENT_ALL_CONTENT_READ,
            PERMISSION_STUDENT_CONTENT_CREATE_OR_UPDATE, PERMISSION_STUDENT_CONTENT_DELETE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions()
                .stream()
                .map(ApplicationUserPermission::getPermissionName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
