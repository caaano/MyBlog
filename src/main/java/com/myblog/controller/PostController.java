package com.myblog.controller;

import com.myblog.config.data.UserSession;
import com.myblog.request.PostCreate;
import com.myblog.request.PostEdit;
import com.myblog.request.PostSearch;
import com.myblog.response.PostResponse;
import com.myblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/foo")
    public String foo(UserSession userSession) {
        log.info(">>> {}", userSession.name);
        return userSession.name;
    }

    @GetMapping("/bar")
    public String bar(UserSession userSession) {
        return "인증이 필요한 페이지";
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request, @RequestHeader String authorization) {

        if (authorization.equals("Jingi")) {
            request.validate();
            postService.write(request);
        }
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long postId) {
       return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request, @RequestHeader String authorization) {
        if (authorization.equals("Jingi")) {
            postService.edit(postId, request);
        }
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId, @RequestHeader String authorization) {
        if (authorization.equals("Jingi")) {
            postService.delete(postId);
        }
    }
}
