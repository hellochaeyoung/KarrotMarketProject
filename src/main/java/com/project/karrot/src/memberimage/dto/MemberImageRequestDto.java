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

    private String fileName;
    private String fileOriName;
    private String fileURL;

    public MemberImage toEntity(Member member) {
        return MemberImage.builder()
                .fileName(fileName)
                .fileOriName(fileOriName)
                .fileURL(fileURL)
                .member(member)
                .build();
    }
}
