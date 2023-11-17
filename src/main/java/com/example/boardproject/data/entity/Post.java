package com.example.boardproject.data.entity;

import com.example.boardproject.data.common.BaseTimeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    //file
    private String filename; //file name
    private String filepath; //file location

    //조회수
    //생성 되면 0으로 시작, null이면 안됨
    @Column(columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private int readCnt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    private Member author;

    @ManyToMany
    Set<Member> voter;

}
