package com.project.karrot.src.member;

import com.project.karrot.src.member.dto.MemberRequestDto;
import com.project.karrot.src.member.dto.MemberResponseDto;

import java.util.Optional;

public interface MemberDetailsService {

    Optional<Member> login();

}
