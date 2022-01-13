package com.project.karrot.controller;

import com.project.karrot.domain.Member;
import com.project.karrot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private Member member;
    private final MemberService memberService;
    //private final ProductService productService;

    // 생성자 주입
    @Autowired
    public MemberController(Member member, MemberService memberService) {
        this.member = member;
        this.memberService = memberService;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm memberForm) {
        Member member = new Member();

        member.setName(memberForm.getName());
        member.setEmail(memberForm.getEmail());
        member.setPassword(memberForm.getPassword());
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setNickName(memberForm.getNickName());
        /////////////////

        System.out.println(member.getName());
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        System.out.println(member.getPhoneNumber());
        System.out.println(member.getNickName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(MemberForm memberForm, Model model) {
        Member member = memberService.findOneByEmail(memberForm.getEmail()).get(); // 로그인 계정 세팅, memberForm에 지역 값 입력하는 거 추가해줘야함
        setMember(member);

        String nickName = member.getNickName(); // 상품 메인 페이지에서 필요
        //long location = member.getLocationId(); // 추후 수정 필요

        model.addAttribute("nickName", nickName);
        //model.addAttribute("location", location);

        //List<Product> products = productService.findByLocationId(member.getLocationId()); // locationId 설정안되서 에러남
        //model.addAttribute("products", products);

        return "mains/mainPage";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

