package com.myblog.controller;

import com.myblog.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    @PostMapping ("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate params) {

        return Map.of();
    }
}
