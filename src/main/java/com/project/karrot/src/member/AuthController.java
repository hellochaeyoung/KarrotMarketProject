package com.project.karrot.src.member;

import com.project.karrot.common.Api.Response;
import com.project.karrot.src.jwt.JwtFilter;
import com.project.karrot.src.jwt.TokenProvider;
import com.project.karrot.src.member.dto.LoginReqDto;
import com.project.karrot.src.member.dto.TokenResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResDto> authorize(@RequestBody LoginReqDto loginReqDto) {

        System.out.println("authorize method access");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword());

        System.out.println("authorize method access");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("authorize method access");
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        System.out.println("authorize method access");

        return new ResponseEntity<>(new TokenResDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
