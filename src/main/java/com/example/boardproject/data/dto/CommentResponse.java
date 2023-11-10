package com.example.boardproject.data.dto;

import com.example.boardproject.data.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class CommentResponse {
    private Integer id;
    private String content;
    private PostResponse post;
    private MemberResponse author;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private Set<MemberResponse> voter;

}
