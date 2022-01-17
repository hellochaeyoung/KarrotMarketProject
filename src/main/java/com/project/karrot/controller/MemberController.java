package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Deal;
import com.project.karrot.domain.InterestedProduct;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final ProductService productService;
    private final LocationService locationService;
    private final DealService dealService;
    private final InterestedService interestedService;

    public MemberController(MemberService memberService, ProductService productService, LocationService locationService, DealService dealService, InterestedService interestedService) {
        this.memberService = memberService;
        this.productService = productService;
        this.locationService = locationService;
        this.dealService = dealService;
        this.interestedService = interestedService;
    }

    // 생성자 주입
    @Autowired


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
        //member.setLocation(memberForm.getLocation()); /////////////
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
    public String login(@ModelAttribute @Validated MemberForm memberForm,
                        BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        //System.out.println(member.hashCode());

        if(bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Member loginMember = memberService.login(memberForm.getEmail(), memberForm.getPassword()); // 로그인 계정 세팅, memberForm에 지역 값 입력하는 거 추가해줘야함

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/loginForm";
        }

        List<Product> products = productService.findByMember(loginMember).orElseGet(ArrayList::new);
        List<Deal> deals = dealService.findByMember(loginMember).orElseGet(ArrayList::new);
        List<InterestedProduct> interestedProducts = interestedService.findInterestedByMember(loginMember).orElseGet(ArrayList::new);

        loginMember.setProducts(products);
        loginMember.setDeals(deals);
        loginMember.setInterestedProducts(interestedProducts);

        HttpSession session = request.getSession(); // 세션 있으면 반환, 없으면 신규 세션 생성
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 정보 보관

        return "redirect:" + redirectURL;
    }

    @PostMapping("/members/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate(); // 세션 날리기
        }

        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

