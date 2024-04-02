package com.myblog.controller;

import com.myblog.config.AppConfig;
import com.myblog.request.Signup;
import com.myblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }
}
