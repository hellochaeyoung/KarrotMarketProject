package com.project.karrot.src.member;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.location.LocationRepository;
import com.project.karrot.src.member.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, LocationRepository locationRepository) {
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
    }

    public MemberResponseDto join(MemberRequestDto memberRequestDto) {

        if(memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 회원입니다.");
        }

        validateDuplicateMember(memberRequestDto); // 닉네임 중복 회원 검증

        Authority authority = Authority.builder()
                .authorityName("ROLE_MEMBER")
                .build();

        // 비밀번호 암호화
        String password = memberRequestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        // 지역 설정
        //Location findLocation = locationRepository.findByAddress(memberRequestDto.getLocationName()).orElseThrow();

        Member member = memberRequestDto.toEntity(encodedPassword, authority);

        return new MemberResponseDto(memberRepository.save(member));

    }

    public MemberResponseDto login() {

        Member loginMember = SecurityUtil.getCurrentMembername().flatMap(memberRepository::findOneWithAuthoritiesByEmail).orElseThrow();

        return new MemberResponseDto(loginMember);

    }

    private void validateDuplicateMember(MemberRequestDto memberRequestDto) {
        memberRepository.findByNickName(memberRequestDto.getNickName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });

    }

    public MemberResponseDto update(Long memberId, String nickName) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.setNickName(nickName);

        return new MemberResponseDto(memberRepository.save(member));
    }

    public MemberResponseDto find(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();

        return new MemberResponseDto(findMember);
    }

    public MemberResponseDto findByName(String name) {
        Member findMember = memberRepository.findByName(name).orElseThrow();

        return new MemberResponseDto(findMember);
    }

    public MemberResponseDto findByNickName(String nickName) {
        Member findMember = memberRepository.findByNickName(nickName).orElseThrow();

        return new MemberResponseDto(findMember);
    }

    public MemberResponseDto findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email).orElseThrow();

        return new MemberResponseDto(findMember);
    }

    public void remove(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
