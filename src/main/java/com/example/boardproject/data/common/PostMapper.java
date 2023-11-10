package com.example.boardproject.data.common;

import com.example.boardproject.data.dto.PostRequest;
import com.example.boardproject.data.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper implements EntityMapper<PostRequest, Post> {
    @Override
    public Post toEntity(PostRequest postRequest) {
        return null;
    }
}
