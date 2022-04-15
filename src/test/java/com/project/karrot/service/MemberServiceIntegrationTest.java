package com.project.karrot.service;

import com.project.karrot.common.exception.exceptions.EmailDuplicatedException;
import com.project.karrot.common.exception.exceptions.NickNameDuplicatedException;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    private String email = "chaeyoung@gmail.com";
    private MemberRequestDto memberRequestDto;
    private MemberResponseDto memberResponseDto;

    @BeforeEach
    void init() {

        memberRequestDto = MemberRequestDto.builder()
                .name("chaeyoung")
                .nickName("cyyyyyy")
                .email(email)
                .locationName("서울특별시")
                .password("aaaa")
                .password("010-1111-2222")
                .build();

        memberResponseDto = memberService.join(memberRequestDto);
    }

    @Test
    public void 회원가입() {

        log.info("{} 님의 회원가입 완료, 닉네임 - {}", memberRequestDto.getEmail(), memberRequestDto.getNickName());

        assertEquals(memberResponseDto.getNickName(), memberRequestDto.getNickName());

    }

    @Test
    public void 회원가입_실패_중복이메일() throws Exception {
        //Given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("cccc")
                .nickName("cdcd")
                .email(email)
                .locationName("서울특별시")
                .password("abcs")
                .password("010-1111-2222")
                .build();

        //When
        EmailDuplicatedException e = assertThrows(EmailDuplicatedException.class,
                () -> memberService.join(memberRequestDto));

        //Then
        assertThat(e.getMessage()).isEqualTo("email duplicated");
    }

    @Test
    public void 회원가입_실패_중복닉네임() {
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("chaeyoung")
                .nickName("cyyyyyy")
                .email("hi@gmail.com")
                .locationName("서울특별시")
                .password("aaaa")
                .password("010-1111-2222")
                .build();

        NickNameDuplicatedException exception = assertThrows(NickNameDuplicatedException.class,
                () -> memberService.join(memberRequestDto));

        assertThat(exception.getMessage()).isEqualTo("nickname duplicated");
    }


}
