package com.project.karrot.service;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hellochaeyoung");
        member.setNickName("hello");

        System.out.println(member.getName());
        //When
        Long saveId = memberService.join(member);
        Member find = memberService.find(saveId).get();
        System.out.println(find.getName());
        member.setName("cyahn");

        //Then
        Member findMember = memberService.find(saveId).get();
        System.out.println(findMember.getName());
        assertEquals(member.getNickName(), findMember.getNickName());
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member = new Member();
        member.setNickName("hello");

        Member member2 = new Member();
        member2.setNickName("hello");

        //When
        memberService.join(member);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        //Then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 닉네임입니다.");
    }

}
