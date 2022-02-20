package com.project.karrot.controller;

import com.project.karrot.constants.SessionConstants;
import com.project.karrot.domain.*;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.dto.ProductResponseDto;
import com.project.karrot.service.CategoryService;
import com.project.karrot.service.LocationService;
import com.project.karrot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public MainController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public String setCategory(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER_LOCATION, required = false) Location loginLocation, Model model, @RequestBody Map<String, String> map) {

        String category = map.get("category");
        Category category1 = categoryService.findByName(category).get();

        List<ProductResponseDto> products = productService.findByLocationAndCategory(loginLocation, category1);
        model.addAttribute("products", products);

        return "mains/mainPage :: #resultTable";
    }

    @GetMapping("/mains/register")
    public String productRegisterForm(Model model) {

        model.addAttribute("allCategory", categoryService.findAll());

        return "mains/productForm";
    }


    @PostMapping("/mains/register")
    public String register(@SessionAttribute(name = SessionConstants.LOGIN_MEMBER, required = false) MemberRequestDto memberRequestDto, ProductRequestDto productRequestDto,
                           @RequestParam(defaultValue = "/mains/mainPage") String redirectURL ) {
        /*
        Product product = new Product();

        Category category = categoryService.findByName(productForm.getCategory()).get();

        Location location = locationService.find(loginMember.getLocation().getLocationId()).get();

        product.setProductName(productForm.getProductName());
        product.setPrice(productForm.getPrice());
        product.setCategory(category);
        product.setContents(productForm.getContents());
        product.setMember(loginMember);
        product.setProductStatus(ProductStatus.SALE);
        product.setLocation(location);
        product.setTime(fomatDate());
         */

        productService.register(productRequestDto, memberRequestDto);

        return "redirect:/";
    }

}

