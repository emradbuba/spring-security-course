package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.gitlab.emradbuba.learning.springsecuritycourse.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ_PERM, COURSE_WRITE_PERM, STUDENT_READ_PERM, STUDENT_WRITE_PERM)),
    ADMIN_JUNIOR(Sets.newHashSet(COURSE_READ_PERM, STUDENT_READ_PERM));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(ApplicationUserPermission::getPermissionName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
