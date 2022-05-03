package com.project.karrot.src.memberimage;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.memberimage.dto.MemberImageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberImageService {

    private final MemberImageRepository memberImageRepository;
    private final MemberRepository memberRepository;

    public String save(MemberImageRequestDto memberImageRequestDto) {
        Member member = memberRepository.findById(memberImageRequestDto.getMemberId()).orElseThrow();

        memberImageRepository.findByMemberId(memberImageRequestDto.getMemberId()).ifPresentOrElse(
                memberImage -> memberImage.setFileURL(memberImageRequestDto.getFileURL()),
        () -> memberImageRepository.save(memberImageRequestDto.toEntity(member)));

        return memberImageRequestDto.getFileURL();
    }

    public String findByMember(Long memberId) {
        MemberImage memberImage = memberImageRepository.findByMemberId(memberId).orElseThrow();
        return memberImage.getFileURL();
    }

    public String update(MemberImageRequestDto memberImageRequestDto) {
        MemberImage memberImage = memberImageRepository.findByMemberId(memberImageRequestDto.getMemberId()).orElseThrow();
        memberImage.setFileURL(memberImageRequestDto.getFileURL());

        return memberImage.getFileURL();
    }

    public void delete(MemberImageRequestDto memberImageRequestDto) {
        memberImageRepository.deleteById(memberImageRequestDto.getImageId());
    }
}
