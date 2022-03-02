package com.project.karrot.src.member.dto;

import com.project.karrot.src.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickName;
    //private final String address;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        //this.address = member.getLocation().getAddress();
    }

}
