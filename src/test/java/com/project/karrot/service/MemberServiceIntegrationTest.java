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


        MemberResponseDto memberRes = memberService.join(memberReq);

        String encodedPassword = memberRes.getPassword();
        System.out.println(memberRes.getId());
        //System.out.println(memberRes.getAddress());
        System.out.println(memberRes.getNickName());

        assertTrue(passwordEncoder.matches("aaaa", encodedPassword));

    }

    @Test
    public void 로그인() {
        MemberRequestDto memberReq = new MemberRequestDto();

        String email = "cyahn@gmail.com";

        memberReq.setName("chaeyoung");
        memberReq.setNickName("cyyyyyy");
        memberReq.setEmail(email);
        memberReq.setLocationName("경기도 부천시 상동");
        memberReq.setPassword("aaaa");
        memberReq.setPhoneNumber("010-1111-1111");


        MemberResponseDto memberRes = memberService.join(memberReq);

        MemberRequestDto loginMember = new MemberRequestDto();
        loginMember.setEmail(email);
        loginMember.setPassword("bdfsdf");
        try {
            memberService.login(loginMember);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("비밀번호가 틀립니다.");
        }

    }

    @Test
    public void 중복이메일_회원_예외() throws Exception {
        //Given
        MemberRequestDto member1 = new MemberRequestDto();
        member1.setEmail("cyahn@naver.com");

        //When
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member1));

        //Then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    public void 중복닉네임_회원_예외() {
        MemberRequestDto member = new MemberRequestDto();
        member.setNickName("부엉");

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> memberService.join(member));

        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 닉네임입니다.");
    }


}
