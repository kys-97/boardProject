package com.example.boardproject.data.dto;

import com.example.boardproject.data.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse extends BaseTimeEntity {
    private Integer id;             // 회원 번호 (PK)
    private String nickname;      //
    private String password;     // 비밀번호
    private String name;         // 이름
}

