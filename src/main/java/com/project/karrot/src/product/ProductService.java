package com.project.karrot.src.product;

import com.project.karrot.src.ProductStatus;
import com.project.karrot.src.category.Category;
import com.project.karrot.src.category.CategoryRepository;
import com.project.karrot.src.location.LocationRepository;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.product.dto.ProductAndImageResponseDto;
import com.project.karrot.src.product.dto.ProductRequestDto;
import com.project.karrot.src.product.dto.ProductResponseDto;
import com.project.karrot.src.product.dto.ProductUpdateRequestDto;
import com.project.karrot.src.productimage.ProductImage;
import com.project.karrot.src.productimage.ProductImageRepository;
import com.project.karrot.src.productimage.dto.ProductImageDto;
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

        Product newProduct = productRequestDto.toEntity(member, findCategory, time);
        Product result = productRepository.save(newProduct);

        // DB 테이블에 이미지 url 저장
        List<ProductImageDto> savedImageList = new ArrayList<>();
        List<String> fileUrlList = productRequestDto.getFileUrlList();
        if(fileUrlList != null) {
            savedImageList = saveImageToDB(result, fileUrlList);
        } // 예외 처리 넣을 것

        ProductResponseDto productResponseDto = new ProductResponseDto(result);
        ProductAndImageResponseDto productAndImageResponseDto = new ProductAndImageResponseDto(productResponseDto, savedImageList);

        productRepository.flush();

        return productAndImageResponseDto;

    }

    public ProductAndImageResponseDto update(ProductUpdateRequestDto productUpdateRequestDto) {

        Product product = productRepository.findById(productUpdateRequestDto.getProductId()).orElseThrow();
        Category category = categoryRepository.findByCategoryName(productUpdateRequestDto.getCategoryName()).orElseThrow();

        // set만 해도 update 적용되는지 추후에 다시 확인 or 따로 메소드 만들지
        product.setProductName(productUpdateRequestDto.getProductName());
        product.setCategory(category);
        product.setPrice(productUpdateRequestDto.getPrice());
        product.setContents(productUpdateRequestDto.getContents());

        List<ProductImageDto> savedImageList = saveImageToDB(product, productUpdateRequestDto.getFileUrlList());

        ProductResponseDto productResponseDto = new ProductResponseDto(product);

        return new ProductAndImageResponseDto(productResponseDto, savedImageList);
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
        List<Product> productList = productRepository.findByLocationId(locationId).orElseGet(ArrayList::new);

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

    private List<ProductImageDto> saveImageToDB(Product product, List<String> urlList) {
        List<ProductImageDto> productImageList = new ArrayList<>();

        urlList.listIterator().forEachRemaining(fileURL -> {
            ProductImage productImage = new ProductImage(product, fileURL);
            ProductImage saveImage = productImageRepository.save(productImage);
            productImageList.add(new ProductImageDto(saveImage.getId(), fileURL));
        });

        return productImageList;
    }

    private String fomatDate() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
        return format.format(now);
    }

}

