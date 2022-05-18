package com.project.karrot.src.interest;

import com.project.karrot.src.interest.dto.InterestedRequestDto;
import com.project.karrot.src.interest.dto.InterestedResponseDto;
import com.project.karrot.src.member.Member;
import com.project.karrot.src.member.MemberRepository;
import com.project.karrot.src.product.Product;
import com.project.karrot.src.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class InterestedService {

    private final InterestedRepository interestedRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public InterestedResponseDto add(InterestedRequestDto interestedRequestDto) {

        Member member = memberRepository.findById(interestedRequestDto.getMemberId()).orElseThrow();
        Product product = productRepository.findById(interestedRequestDto.getProductId()).orElseThrow();

        int count = product.getLikeCount();
        product.setLikeCount(++count);

        InterestedProduct interestedProduct = interestedRequestDto.toEntity(member, product);

        return new InterestedResponseDto(interestedRepository.save(interestedProduct));
    }

    public InterestedResponseDto find(Long interestedId) {
        InterestedProduct interestedProduct = interestedRepository.findById(interestedId).orElseThrow();

        return new InterestedResponseDto(interestedProduct);
    }

    public List<InterestedResponseDto> findInterestedByMemberIdAndProductStatus(Long memberId) {
        List<InterestedProduct> list = interestedRepository.findByMemberId(memberId).orElseGet(ArrayList::new);

        return list.stream()
                .map(InterestedResponseDto::new)
                .collect(Collectors.toList());

    }

    public List<InterestedResponseDto> findInterestedByProduct(Long productId) {
        List<InterestedProduct> list = interestedRepository.findByProductId(productId).orElseGet(ArrayList::new);

        return list.stream()
                .map(InterestedResponseDto::new)
                .collect(Collectors.toList());
    }

    public int remove(InterestedRequestDto interestedRequestDto) {

        Product product = productRepository.findById(interestedRequestDto.getProductId()).orElseThrow();

        int cnt = product.getLikeCount();
        product.setLikeCount(--cnt);

        interestedRepository.deleteByMemberId(interestedRequestDto.getMemberId());

        return product.getLikeCount();
    }
}
