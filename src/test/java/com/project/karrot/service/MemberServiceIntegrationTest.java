package com.project.karrot.service;

import com.project.karrot.domain.Member;
import com.project.karrot.repository.MemberRepository;
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

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setNickName("hello");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
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
