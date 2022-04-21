package com.project.karrot.src.member.dto;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.member.Authority;
import com.project.karrot.src.member.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 10, max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 50)
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    @Size(min = 1, max = 50)
    private String nickName;

    @NotBlank
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
