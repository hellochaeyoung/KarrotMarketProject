package com.project.karrot.src.member;

import com.project.karrot.src.member.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberAuthService implements MemberDetailsService{

    private final MemberRepository memberRepository;

    public Optional<Member> login() {
        return SecurityUtil.getCurrentMembername().flatMap(memberRepository::findOneWithAuthoritiesByEmail);
    }

}
