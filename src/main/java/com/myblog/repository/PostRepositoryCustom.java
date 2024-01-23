package com.myblog.repository;

import com.myblog.domain.Post;
import com.myblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
