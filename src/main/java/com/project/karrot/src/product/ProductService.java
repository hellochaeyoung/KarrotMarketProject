package com.project.karrot.src.product;

import com.project.karrot.src.location.Location;
import com.project.karrot.src.location.LocationRepository;
import com.project.karrot.src.product.dto.ProductAndImageResponseDto;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import com.project.karrot.src.category.CategoryRepository;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.productimage.ProductImage;
import com.project.karrot.src.productimage.ProductImageRepository;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final ProductImageRepository productImageRepository;

    public ProductAndImageResponseDto register(ProductRequestDto productRequestDto) {

        Member member = memberRepository.findById(productRequestDto.getMemberId()).orElseThrow();
        Category findCategory = categoryRepository.findByCategoryName(productRequestDto.getCategoryName()).orElseThrow();
        String time = fomatDate();

        //productRequestDto.setProductRequestDto(member, findCategory, time);
        Product newProduct = productRequestDto.toEntity(member, findCategory, time);

        Product result = productRepository.save(newProduct);

        List<String> fileUrlList = new ArrayList<>();
        productRequestDto.getFileUrlList().listIterator().forEachRemaining(fileURL -> {
            ProductImage productImage = new ProductImage(result, fileURL);
            productImageRepository.save(productImage);
            fileUrlList.add(fileURL);
        });

        ProductResponseDto productResponseDto = new ProductResponseDto(result);

        ProductAndImageResponseDto productAndImageResponseDto = new ProductAndImageResponseDto(productResponseDto, fileUrlList);

        productRepository.flush();

        return productAndImageResponseDto;

    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

    public ProductResponseDto update(ProductRequestDto productRequestDto) {

        Product product = productRepository.findById(productRequestDto.getProductId()).orElseThrow();
        Category category = categoryRepository.findByCategoryName(productRequestDto.getCategoryName()).orElseThrow();

        product.setProductName(productRequestDto.getProductName());
        product.setCategory(category);
        product.setPrice(productRequestDto.getPrice());
        product.setContents(productRequestDto.getContents());

        // set만 해도 update 적용되는지 추후에 다시 확인

        return new ProductResponseDto(product);
    }

    public void updateStatus(ProductRequestDto productRequestDto, ProductStatus status) {
        Product product = productRepository.findById(productRequestDto.getProductId()).orElseThrow();
        product.setProductStatus(status);
    }

    public ProductResponseDto findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> findByLocation(Long locationId) {
        List<Product> productList = productRepository.findByLocation(locationId).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByLocationAndCategory(Long locationId, Long categoryId) {
        List<Product> productList = productRepository.findByLocationIdAndCategoryId(locationId, categoryId).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByMemberId(Long memberId) {
        List<Product> productList = productRepository.findByMemberId(memberId).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findByMemberAndStatus(Long memberId, ProductStatus status) {
        List<Product> productList = productRepository.findByMemberIdAndProductStatus(memberId, status).orElseGet(ArrayList::new);

        return productList.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    public void remove(Long productId) {
        productRepository.deleteById(productId);
    }

}

