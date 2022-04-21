package com.project.karrot.src.member;

import com.project.karrot.src.jwt.JwtFilter;
import com.project.karrot.src.jwt.TokenProvider;
import com.project.karrot.src.member.dto.MemberLoginRequestDto;
import com.project.karrot.src.member.dto.TokenResDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberAuthService memberAuthService;

    @ApiOperation(value = "로그인 - 인증", notes = "이메일과 비밀번호가 일치하는지 확인한다.")
    @PostMapping("/auth")
    public ResponseEntity<TokenResDto> authorize(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenResDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인 - 완료", notes = "인증 되면 로그인을 성공하고 완료한다.")
    @GetMapping("/user")
    public ResponseEntity<?> finish() {
        return ResponseEntity.ok(memberAuthService.login());
    }
}
