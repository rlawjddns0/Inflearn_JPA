package com.example.jpabook.service;

import com.example.jpabook.embedded.Address;
import com.example.jpabook.entity.Member;
import com.example.jpabook.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void joinTest() throws Exception {
        //given
        Member member=new Member();
        member.setUsername("KIM");
        Address address = new Address("asdfasdf","sdfsdf","dsldf");
        member.setAddress(address);

        //when
        Long savedId = memberService.join(member);


        //then
        Assert.assertEquals(member,memberRepository.find(savedId));



    }

    @Test(expected = IllegalStateException.class)//예외가 발생하면 얘가 떠야 한다.
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1=new Member();
        member1.setUsername("KIM1");

        Member member2 = new Member();
        member2.setUsername("KIM1");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.

        //then
        fail("예외가 발생하지 않았다.");

    }

}