package com.project.karrot.dto;

import com.project.karrot.domain.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String nickName;
    private String location;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .build();
    }


}
