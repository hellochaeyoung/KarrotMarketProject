package com.project.karrot.src.member.dto;

import com.project.karrot.src.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String locationName;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.locationName = member.getLocation().getAddress();
    }

}
