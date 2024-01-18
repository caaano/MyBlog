package com.myblog.controller;

import com.myblog.request.PostCreate;
import com.myblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping ("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }
}
