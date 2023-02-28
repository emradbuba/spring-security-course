package com.gitlab.emradbuba.learning.springsecuritycourse.security;

public enum ApplicationUserPermission {
    STUDENT_READ_PERM("student:read"),
    STUDENT_WRITE_PERM("student:write"),
    COURSE_READ_PERM("course:read"),
    COURSE_WRITE_PERM("course:write");

    private final String permission;

    ApplicationUserPermission(String permissionName) {
        this.permission = permissionName;
    }

    public String getPermissionName() {
        return permission;
    }
}
