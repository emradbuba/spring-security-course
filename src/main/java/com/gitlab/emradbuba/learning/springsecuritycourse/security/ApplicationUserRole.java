package com.gitlab.emradbuba.learning.springsecuritycourse.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.gitlab.emradbuba.learning.springsecuritycourse.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(CAN_READ_BASIC_CONTENT)),
    JUNIOR_ADMIN(Sets.newHashSet(CAN_READ_BASIC_CONTENT, CAN_READ_FULL_CONTENT,
            CAN_CREATE_AND_UPDATE)),
    MAIN_ADMIN(Sets.newHashSet(CAN_READ_BASIC_CONTENT, CAN_READ_FULL_CONTENT,
            CAN_CREATE_AND_UPDATE, CAN_DELETE, CAN_ACCESS_TOP_SECRET_DATA));

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
