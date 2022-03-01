package com.project.karrot.src.member.dto;

import com.project.karrot.src.auth.Salt;
import com.project.karrot.src.deal.Deal;
import com.project.karrot.src.interest.InterestedProduct;
import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickName;
    private final Location location;
    private final Salt salt;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.location = member.getLocation();
        this.salt = member.getSalt();
    }

}
