package com.project.karrot.service;

import com.project.karrot.domain.*;
import com.project.karrot.repository.ProductRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product register(Product product) {

        Product result = productRepository.save(product);

        productRepository.flush();

        return result;

    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    public Optional<Product> find(Long productId) {
        return productRepository.findById(productId);
    }

    public Optional<List<Product>> findByLocation(Location location) {
        return productRepository.findByLocation(location);
    }

    public Optional<List<Product>> findByLocationAndCategory(Location location, Category category) {
        return productRepository.findByLocationAndCategory(location, category);
    }

    public Optional<List<Product>> findByMember(Member member) {
        return productRepository.findByMember(member);
    }

    public Optional<List<Product>> findByMemberAndStatus(Member member, ProductStatus status) {
        return productRepository.findByMemberAndStatus(member, status);
    }

    public void remove(Product product) {
        productRepository.delete(product);
    }

}

