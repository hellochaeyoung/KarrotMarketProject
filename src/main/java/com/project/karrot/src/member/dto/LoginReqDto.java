package com.project.karrot.src.member.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDto {

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;
}
