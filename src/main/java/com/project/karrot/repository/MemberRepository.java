package com.project.karrot.repository;

import com.project.karrot.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    Optional<Member> findByNickName(String nickName);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
}
