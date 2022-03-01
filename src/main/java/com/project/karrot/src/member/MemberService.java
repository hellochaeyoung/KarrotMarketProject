package com.project.karrot.src.member;

import com.project.karrot.src.auth.Salt;
import com.project.karrot.src.auth.SaltUtil;
import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;
import com.project.karrot.src.location.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

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

    public void join(MemberRequestDto memberRequestDto) {

        validateDuplicateMember(memberRequestDto); // 닉네임 중복 회원 검증

        String password = memberRequestDto.getPassword();

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        memberRequestDto.setMemberAuth(encodedPassword);

        // 지역 설정
        Location findLocation = locationRepository.findByAddress(memberRequestDto.getLocationName()).orElseThrow();
        memberRequestDto.setMemberRequestDto(findLocation);

        Member member = memberRequestDto.toEntity();

        memberRepository.save(member);

    }

    public MemberResponseDto login(MemberRequestDto memberRequestDto) throws Exception{
        MemberResponseDto loginMember = findByEmail(memberRequestDto.getEmail());

        if(!passwordEncoder.matches(memberRequestDto.getPassword(), loginMember.getPassword())) {
            throw new Exception("비밀번호가 틀립니다.");
        }

        return loginMember;
    }

    private void validateDuplicateMember(MemberRequestDto memberRequestDto) {
        // findByNickName은 Optional을 리턴하는데 Optional을 써주는건 좋지않다.
        // 바로 메소드를 붙여 사용하는 게 더 좋은 방법이다.
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
