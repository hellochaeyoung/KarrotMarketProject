package com.project.karrot.service;

import com.project.karrot.domain.Location;
import com.project.karrot.domain.Member;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.repository.LocationRepository;
import com.project.karrot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    // 직접 new 해서 생성하는 게 아닌 외부에서 집어넣을 수 있게
    public MemberService(MemberRepository memberRepository, LocationRepository locationRepository) {
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
    }

    public Long join(MemberRequestDto memberRequestDto) {

        validateDuplicateMember(memberRequestDto); // 닉네임 중복 회원 검증

        Member member = memberRequestDto.toEntity();
        Location findLocation = locationRepository.findByName(memberRequestDto.getLocation()).orElseThrow(NullPointerException::new).get(0);
        member.setLocation(findLocation);

        memberRepository.save(member);

        memberRepository.flush();

        return member.getId();
    }

    private void validateDuplicateMember(MemberRequestDto memberRequestDto) {
        // findByNickName은 Optional을 리턴하는데 Optional을 써주는건 좋지않다.
        // 바로 메소드를 붙여 사용하는 게 더 좋은 방법이다.
        memberRepository.findByNickName(memberRequestDto.getNickName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }

    public Optional<Member> find(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    public MemberResponseDto login(MemberRequestDto memberRequestDto) {
        Member findMember = memberRepository.findByEmail(memberRequestDto.getEmail())
                .filter(member -> member.getPassword().equals(memberRequestDto.getPassword()))
                .orElse(null);
        if(findMember == null) {
            return null;
        }else {
            return new MemberResponseDto(findMember);
        }

    }

    public void remove(Member member) {
        memberRepository.delete(member);
    }


}
