package com.project.karrot.src.memberimage;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.memberimage.dto.MemberImageRequestDto;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@Transactional
@AllArgsConstructor
public class MemberImageService {

    private final MemberImageRepository memberImageRepository;
    private final MemberRepository memberRepository;

    public void save(MemberImageRequestDto memberImageRequestDto) {
        Member member = memberRepository.findById(memberImageRequestDto.getMemberId()).orElseThrow();

        MemberImage image = memberImageRequestDto.toEntity(member);
        memberImageRepository.save(image);
    }

    public void findByMember(Long memberId) {
        memberImageRepository.findByMemberId(memberId);
    }

    public void findByFileURL(String url) {
        memberImageRepository.findByFileURL(url);
    }
}
