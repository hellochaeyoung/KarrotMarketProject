package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Category;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public Member member;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if(loginMember == null) {
            return "home";
        }

        // Test용
        List<Product> products = productService.findByMember(loginMember).orElseGet(ArrayList::new);
        //List<Product> products = productService.findByLocation(loginMember.getLocation()).orElseGet(ArrayList::new);
        model.addAttribute("products", products);

        List<Category> allCategory = categoryService.findAll();
        model.addAttribute("allCategory", allCategory);

        model.addAttribute("member", loginMember);

        return "/mains/mainPage";
    }
}

