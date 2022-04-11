package com.project.karrot.src.productimage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    Optional<List<ProductImage>> findByProductId(Long productId);

}
