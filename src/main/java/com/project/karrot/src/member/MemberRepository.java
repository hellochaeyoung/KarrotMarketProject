package com.project.karrot.src.member;

import com.project.karrot.src.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByEmail(String email);
}
