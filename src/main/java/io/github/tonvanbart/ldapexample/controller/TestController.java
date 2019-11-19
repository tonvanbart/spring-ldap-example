package io.github.tonvanbart.ldapexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Initial "Hello World" controller
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Authentication getUserinfo() {
        log.info("getUserinfo()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping(value = "principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal getPrincipal(Principal principal) {
        log.info("getPrincipal()");
        return principal;
    }

}
