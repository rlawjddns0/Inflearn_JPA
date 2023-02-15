package com.example.jpabook.repository;

import com.example.jpabook.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository2 extends JpaRepository<Member, Long> {
    List<Member> findByusername(String name);
}
