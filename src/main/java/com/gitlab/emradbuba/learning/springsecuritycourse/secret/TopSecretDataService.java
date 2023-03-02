package com.gitlab.emradbuba.learning.springsecuritycourse.secret;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

/**
 * Class contains methods which should be available only for the most privileged "admin".
 * Security will be implemented here in an annotation-based way.
 */
@Service
@Slf4j
public class TopSecretDataService {

    @Secured("ROLE_MAIN_ADMIN")
    public String getTopSecreteInfo1() {
        return "THIS IS TOP SECRET INFO 1";
    }

    @RolesAllowed("ROLE_MAIN_ADMIN")
    public String getTopSecreteInfo2() {
        return "THIS IS TOP SECRET INFO 2";
    }

    @PreAuthorize("hasAuthority('permission:read-secret')")  // "has(Any)Role('ROLE_MAIN_ADMIN')" / "has(Any)Authority('permission:read-secret// ')"
    // Also as post auth: @PostAuthorize("returnObject.username == authentication.principal.nickName")
    public String getTopSecreteInfo3() {
        return "THIS IS TOP SECRET INFO 3";
    }
}
