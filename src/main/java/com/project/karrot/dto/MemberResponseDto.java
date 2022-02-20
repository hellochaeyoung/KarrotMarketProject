package com.project.karrot.dto;

import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Location;
import com.project.karrot.domain.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String nickName;
    private final Location location;
    private List<Deal> deals;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.location = member.getLocation();
        this.deals = member.getDeals();
    }

}
