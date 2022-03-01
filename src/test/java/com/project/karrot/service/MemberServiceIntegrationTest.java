package com.project.karrot.service;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입() {
        MemberRequestDto memberReq = new MemberRequestDto();

        String email = "cyahn@gmail.com";

        memberReq.setName("chaeyoung");
        memberReq.setNickName("cyyyyyy");
        memberReq.setEmail(email);
        memberReq.setLocationName("경기도 부천시 상동");
        memberReq.setPassword("aaaa");
        memberReq.setPhoneNumber("010-1111-1111");

        memberService.join(memberReq);
        MemberResponseDto memberRes = memberService.findByEmail(email);

        String encodedPassword = memberRes.getPassword();
        System.out.println(memberRes.getId());
        //System.out.println(memberRes.getAddress());
        System.out.println(memberRes.getNickName());

        assertTrue(passwordEncoder.matches("aaaa", encodedPassword));

    }

/*
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

 */

}
