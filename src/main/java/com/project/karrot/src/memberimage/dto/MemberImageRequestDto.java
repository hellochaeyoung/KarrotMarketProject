package com.project.karrot.src.memberimage.dto;

import com.project.karrot.src.member.Member;
import com.project.karrot.src.memberimage.MemberImage;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberImageRequestDto {

    private Long imageId;
    private Long memberId;
    private String fileURL;

    public MemberImage toEntity(Member member) {
        return MemberImage.builder()
                .fileURL(fileURL)
                .member(member)
                .build();
    }

    public void toReady(Long memberId, String url) {
        this.memberId = memberId;
        this.fileURL = url;
    }
}
