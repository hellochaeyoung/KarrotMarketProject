package com.project.karrot.src.member;

import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.member.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
public class MemberAuthService implements MemberDetailsService{

    private final MemberRepository memberRepository;

    public Optional<Member> login() {
        return SecurityUtil.getCurrentMembername().flatMap(memberRepository::findOneWithAuthoritiesByEmail);
    }

}
