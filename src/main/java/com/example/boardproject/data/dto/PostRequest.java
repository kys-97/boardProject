package com.example.boardproject.data.dto;

import com.example.boardproject.data.entity.Post;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @NotEmpty(message = "제목은 필수항목입니다") //Validation 어노테이션 (컨트롤러에서 확인)
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다")
    private String content;

    public PostRequest(Post entity) {

        this.subject = entity.getSubject();
        this.content = entity.getContent();
    }
}
