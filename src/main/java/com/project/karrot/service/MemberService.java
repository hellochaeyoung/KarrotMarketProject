package com.project.karrot.service;

import com.project.karrot.domain.Location;
import com.project.karrot.domain.Member;
import com.project.karrot.dto.LocationResponseDto;
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

    public MemberService(MemberRepository memberRepository, LocationRepository locationRepository) {
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
    }

    public MemberResponseDto join(MemberRequestDto memberRequestDto) {

        validateDuplicateMember(memberRequestDto); // 닉네임 중복 회원 검증

        // 지역 설정
        Location findLocation = locationRepository.findByAddress(memberRequestDto.getLocationName()).orElseThrow();
        memberRequestDto.setMemberRequestDto(findLocation);

        Member member = memberRequestDto.toEntity();

        memberRepository.save(member);

        //memberRepository.flush();

        return new MemberResponseDto(member);
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

    public MemberResponseDto login(MemberRequestDto memberRequestDto) {
        Member findMember = memberRepository.findByEmail(memberRequestDto.getEmail())
                .filter(member -> member.getPassword().equals(memberRequestDto.getPassword()))
                .orElse(null); // 추후 수정 필요, 이렇게 null 리턴하면 옵셔널 쓰는 의미가 없음

        if(findMember == null) {
            return null;
        }else {
            return new MemberResponseDto(findMember);
        }

    }

    public void remove(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
