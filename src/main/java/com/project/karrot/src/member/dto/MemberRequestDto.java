package com.project.karrot.src.member.dto;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Authority;
import com.project.karrot.src.member.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    private Long memberId;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 10, max = 50)
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    private String phoneNumber;

    @NotNull
    @Size(min = 1, max = 50)
    private String nickName;

    private String locationName;

    public Member toEntity(String password, Authority authority, Location location) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .authorities(Collections.singleton(authority))
                .activated(true)
                .location(location)
                .build();
    }
/*
    public void setMemberRequestDto(Location location) {
        this.location = location;
    }

    public void setMemberAuth(String encodePassword) {
        this.password = encodePassword;

    }

 */

}
