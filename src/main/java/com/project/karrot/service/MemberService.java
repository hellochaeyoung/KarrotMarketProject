package com.project.karrot.service;

import com.project.karrot.domain.Member;
import com.project.karrot.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 직접 new 해서 생성하는 게 아닌 외부에서 집어넣을 수 있게
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {

        validateDuplicateMember(member); // 닉네임 중복 회원 검증

        memberRepository.save(member);

        memberRepository.flush();

        return member.getId();
    }

    // 이렇게 메소드로 빼주는 게 좋다.
    private void validateDuplicateMember(Member member) {
        // findByNickName은 Optional을 리턴하는데 Optional을 써주는건 좋지않다.
        // 바로 메소드를 붙여 사용하는 게 더 좋은 방법이다.
        memberRepository.findByNickName(member.getNickName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });


        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일입니다.");
                });
    }

    public Optional<Member> find(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    public Member login(String email, String password) {
        return memberRepository.findByEmail(email)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null); }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public void remove(Member member) {
        memberRepository.delete(member);
    }


}
