package com.example.boardproject.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CommentResponse {
    private Integer id;
    private String content;
    private PostResponse post;
    private MemberResponse author;
    private Set<MemberResponse> voter;
}
