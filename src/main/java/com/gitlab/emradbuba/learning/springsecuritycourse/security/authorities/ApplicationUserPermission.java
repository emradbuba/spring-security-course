package com.gitlab.emradbuba.learning.springsecuritycourse.security.authorities;

public enum ApplicationUserPermission {
    CAN_READ_BASIC_CONTENT("permission:read-basic"),
    CAN_READ_FULL_CONTENT("permission:read-full"),
    CAN_CREATE_AND_UPDATE("permission:create-and-update"),
    CAN_DELETE("permission:delete"),
    CAN_ACCESS_TOP_SECRET_DATA("permission:read-secret");

    private final String permissionName;

    ApplicationUserPermission(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }
}
