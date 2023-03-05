package com.gitlab.emradbuba.learning.springsecuritycourse.rest;

import com.gitlab.emradbuba.learning.springsecuritycourse.secret.TopSecretDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/unsecured")
public class UnsecuredController {
    private final TopSecretDataService topSecretDataService;

    public UnsecuredController(TopSecretDataService topSecretDataService) {
        this.topSecretDataService = topSecretDataService;
    }

    @GetMapping("/public")
    public String getPublicInfo(){
        log.info("Performing public action for user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return "THIS IS PUBLIC INFO";
    }

    @GetMapping("/secret1")
    public String getSecretInfo1(){
        String topSecreteInfo = topSecretDataService.getTopSecreteInfo1();
        log.info("Performing top secret action for user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return topSecreteInfo;
    }

    @GetMapping("/secret2")
    public String getSecretInfo2(){
        String topSecreteInfo = topSecretDataService.getTopSecreteInfo2();
        log.info("Performing top secret action for user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return topSecreteInfo;
    }

    @GetMapping("/secret3")
    public String getSecretInfo3(){
        String topSecreteInfo = topSecretDataService.getTopSecreteInfo3();
        log.info("Performing top secret action for user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        return topSecreteInfo;
    }


}
