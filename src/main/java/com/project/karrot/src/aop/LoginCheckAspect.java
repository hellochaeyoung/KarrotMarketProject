package com.project.karrot.src.aop;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Aspect
@RequiredArgsConstructor
@Slf4j
public class LoginCheckAspect {

    private final MemberAuthService memberAuthService;

    /*
        로그인 되어있는지 체크하는 메소드
        로그인 안되어있을 시 Exception 발생
     */

    @Before("@annotation(com.project.karrot.src.annotation.LoginCheck) && @annotation(target)")
    public void loginCheck() throws HttpClientErrorException{
        log.info("AOP - @LoginCheck 실행");
        Optional<Member> member = memberAuthService.login();
        if(member.isEmpty()) {
            log.info("로그인 확인 실패 - 에러 {} 전송", HttpStatus.UNAUTHORIZED);
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        log.info("로그인 확인 성공 - 로그인한 유저의 접근 : {}", member.get().getEmail());
    }
}
