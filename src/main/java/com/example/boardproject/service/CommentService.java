package com.example.boardproject.service;

import com.example.boardproject.config.DataNotFoundException;
import com.example.boardproject.data.dto.CommentResponse;
import com.example.boardproject.data.dto.MemberResponse;
import com.example.boardproject.data.dto.PostResponse;
import com.example.boardproject.data.entity.Comment;
import com.example.boardproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    private Comment of(CommentResponse commentResponse) {

        return modelMapper.map(commentResponse, Comment.class);
    }
    private CommentResponse of(Comment comment) {

        return modelMapper.map(comment, CommentResponse.class);
    }


    public CommentResponse create(PostResponse postResponse, String content, MemberResponse author) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
//        commentResponse.setCreatedDate(LocalDateTime.now());
        commentResponse.setPost(postResponse);
        commentResponse.setAuthor(author);
        Comment comment = of(commentResponse);
        comment = this.commentRepository.save(comment);
        commentResponse.setId(comment.getId());
        return commentResponse;
    }

    public CommentResponse getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return  of(comment.get());
        }else {
            throw new DataNotFoundException("POST NOT FOUND");
        }
    }

  //delete
    public void delete(CommentResponse commentResponse) {
        this.commentRepository.deleteById(commentResponse.getId());
    }

    //update
    public CommentResponse update(CommentResponse commentResponse, String content) {
        commentResponse.setContent(content);
        Comment comment = of(commentResponse);
        this.commentRepository.save(comment);
        return commentResponse;
    }
}
