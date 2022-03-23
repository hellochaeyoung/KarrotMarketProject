package com.project.karrot.src.aop;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberService;
import com.project.karrot.src.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginCheckAspect {

    private final MemberService memberService;

    /*
        로그인 되어있는지 체크하는 메소드
        로그인 안되어있을 시 Exception 발생
     */

    @Before("@annotation(com.project.karrot.src.annotation.LoginCheck) && @annotation(target)")
    public void loginCheck() throws HttpClientErrorException{
        Optional<Member> member = memberService.login();
        if(member.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
