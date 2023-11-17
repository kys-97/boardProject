package com.example.boardproject.data.dto;

import com.example.boardproject.data.entity.Comment;
import com.example.boardproject.data.entity.Member;
import com.example.boardproject.data.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@Component
public class PostResponse {

    private Integer id;
    private String subject;
    private String content;
    private List<Comment> commentList;
    private Member author;
    private int readCnt;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private Set<MemberResponse> voter;
    //file
    private String filename; //file name
    private String filepath; //file location

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.content = entity.getContent();
        this.readCnt = entity.getReadCnt();
        this.author = entity.getAuthor();
        this.commentList = entity.getCommentList();
        this.updatedDate = entity.getModifiedDate();
        this.filename = entity.getFilename();
        this.filepath = entity.getFilepath();
    }

}
