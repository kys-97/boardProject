package com.example.boardproject.repository;

import com.example.boardproject.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findBynickname(String nickname);
}
