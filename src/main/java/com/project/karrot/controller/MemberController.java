package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.MemberResponseDto;
import com.project.karrot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // 생성자 주입
    @Autowired
    public MemberController(MemberService memberService, ProductService productService, LocationService locationService, DealService dealService, InterestedService interestedService) {
        this.memberService = memberService;
        this.productService = productService;
        this.locationService = locationService;
        this.dealService = dealService;
        this.interestedService = interestedService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String getAll(Model model, @RequestParam("address") String address) {

        if(address.length() == 0) {
            model.addAttribute("locationList", null);
        }else {
            model.addAttribute("locationList", locationService.findByName(address).orElseGet(ArrayList::new));
        }

        return "members/createMemberForm :: #resultLocationList";
    }

    @PostMapping("/members/new")
    public String create(MemberRequestDto memberRequestDto) {
        //Member member = new Member();

        /*
        member.setName(memberForm.getName());
        member.setEmail(memberForm.getEmail());
        member.setPassword(memberForm.getPassword());
        member.setPhoneNumber(memberForm.getPhoneNumber());
        member.setNickName(memberForm.getNickName());

        Location location = locationService.findByName(memberForm.getLocation()).get().get(0);
        member.setLocation(location);
         */

        memberService.join(memberRequestDto);

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String loginForm() {
        return "members/loginForm";
    }

    @PostMapping("/members/login")
    public String login(@ModelAttribute @Validated MemberRequestDto memberRequestDto,
                        BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        MemberResponseDto loginMember = memberService.login(memberRequestDto); // 로그인 계정 세팅, memberForm에 지역 값 입력하는 거 추가해줘야함

        if(loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/loginForm";
        }

        //productService.findByMember(loginMember).ifPresent(loginMember::setProducts);
        //dealService.findByMember(loginMember).ifPresent(loginMember::setDeals);
        //interestedService.findInterestedByMember(loginMember).ifPresent(loginMember::setInterestedProducts);

        //Location location = locationService.find(loginMember.getLocation().getLocationId()).get();
        //loginMember.setLocation(location);

        HttpSession session = request.getSession(); // 세션 있으면 반환, 없으면 신규 세션 생성
        session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember); // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConstants.LOGIN_MEMBER_LOCATION, loginMember.getLocation());

        return "redirect:/";
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate(); // 세션 날리기
        }

        return "redirect:/";

    }
}

