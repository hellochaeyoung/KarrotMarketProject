package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;

    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/mains/register")
    public String productRegisterForm() {
        return "mains/productForm";
    }


    @PostMapping("/mains/register")
    public String register(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) Member loginMember, ProductForm productForm,
                           @RequestParam(defaultValue = "/mains/mainPage") String redirectURL ) {
        Product product = new Product();

        product.setProductName(productForm.getProductName());
        product.setPrice(productForm.getPrice());
        //product.setCategoryId(1); // 수정 필요
        product.setContents(productForm.getContents());
        product.setMember(loginMember); //////////////////////
        System.out.println("############" + loginMember.getNickName());

        productService.register(product); //////////////////

        return "redirect:" + redirectURL;
    }



}

