package com.sda_project.myfluffy.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> profile(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }

}