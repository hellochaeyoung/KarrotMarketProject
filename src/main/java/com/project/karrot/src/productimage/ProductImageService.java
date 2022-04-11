package com.project.karrot.src.productimage;

import com.project.karrot.src.productimage.dto.ProductImageRequestDto;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@Transactional
@AllArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public void update(ProductImageRequestDto productImageRequestDto) {

    }

    public void findByProduct(Long productId) {

    }

    public void delete(Long productId) {

    }
}
