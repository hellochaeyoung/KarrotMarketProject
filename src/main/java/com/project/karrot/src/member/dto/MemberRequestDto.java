package com.project.karrot.src.member.dto;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    private Long memberId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String nickName;
    private String locationName;

    private Location location;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .location(location)
                .build();
    }

    public void setMemberRequestDto(Location location) {
        this.location = location;
    }

}