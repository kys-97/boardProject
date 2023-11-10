package com.example.boardproject.data.entity;

import com.example.boardproject.data.common.BaseTimeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Member author;


    @ManyToMany
    Set<Member> voter;
}
