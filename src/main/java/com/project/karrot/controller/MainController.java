package com.project.karrot.controller;

import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    private final Member member;

    @Autowired
    public MainController(Member member) {
        this.member = member;
    }

    /*
    @GetMapping("/mains/mainPage")
    public String show(Model model) {



        return "mains/mainPage";
    }

     */

    @GetMapping("/mains/register")
    public String productRegisterForm() {
        return "mains/productForm";
    }


    @PostMapping("/mains/register")
    public String register(ProductForm productForm) {
        Product product = new Product();

        product.setProductName(productForm.getProductName());
        product.setPrice(productForm.getPrice());
        //product.setCategoryId(1); // 수정 필요
        product.setContents(productForm.getContents());
        //product.setMemberId(member.getId());

        //productService.register(product); //////////////////

        return "mains/mainPage";
    }



}

