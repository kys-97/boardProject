package com.example.boardproject.data.entity;

import com.example.boardproject.data.common.BaseTimeEntity;
import com.example.boardproject.data.common.MemberRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Component
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;             // 회원 번호 (PK)

    @Column(unique = true)
    private String nickname;      //

    @Column(name = "password")
    private String password;     // 비밀번호

    @Column(name = "name")
    private String name;         // 이름

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole = MemberRole.USER;

    @Builder
    public Member(String nickname, String password, String name) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
    }
}
