package com.example.boardproject.data.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass //BaseTimeEntity -> JPA 엔터티의 공통 매핑 정보를 포함하는 클래스라는 의미
public class BaseTimeEntity {

    @Column(name = "createdDate")
    private LocalDateTime createdDate; //생성
    @Column(name = "modifiedDate")
    private LocalDateTime modifiedDate; //수정

    //PrePersist -> db 들어가기 전에 check
    //JPA 엔티티가 INSERT 되기 전에 실행될 메서드 지정
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}
