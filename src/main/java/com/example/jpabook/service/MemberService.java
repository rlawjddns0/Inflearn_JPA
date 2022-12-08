package com.example.jpabook.service;

import com.example.jpabook.entity.Member;
import com.example.jpabook.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //자동으로 빈으로 등록
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;//변경될 일 없기 때문에 final 키워드 사용
//     롬복 RequiredArgsConstructor 사용 해서 생략
//    @Autowired
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //애만 읽기전용 아니라는걸 설정
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //회원 중복 검사
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers=memberRepository.findByName(member.getUsername());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 검색
    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }

}
