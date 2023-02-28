package com.gitlab.emradbuba.learning.springsecuritycourse.security;

public enum ApplicationUserPermission {
    PERMISSION_STUDENT_BASIC_CONTENT_READ("student:read-basic"),
    PERMISSION_STUDENT_ALL_CONTENT_READ("student:read-all"),
    PERMISSION_STUDENT_CONTENT_CREATE_OR_UPDATE("student:create-update"),
    PERMISSION_STUDENT_CONTENT_DELETE("student:delete");

    private final String permissionName;

    ApplicationUserPermission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }
}
