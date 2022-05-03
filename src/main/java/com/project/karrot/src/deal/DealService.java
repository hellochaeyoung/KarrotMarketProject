package com.project.karrot.src.deal;

import com.project.karrot.src.deal.dto.DealRequestDto;
import com.project.karrot.src.deal.dto.DealResponseDto;
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
public class DealService {

    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public DealResponseDto find(Long dealId) {

        Deal deal = dealRepository.findById(dealId).orElseThrow();

        return new DealResponseDto(deal);
    }

    public DealResponseDto findByProduct(Long productId) {

        Deal deal = dealRepository.findByProductId(productId).orElseThrow();

        return new DealResponseDto(deal);
    }
    public List<DealResponseDto> findByMember(Long memberId) {

        List<Deal> list = dealRepository.findByMemberId(memberId).orElseGet(ArrayList::new);

        return list.stream()
                .map(DealResponseDto::new)
                .collect(Collectors.toList());
    }

    public DealResponseDto register(DealRequestDto deal) {

        Member member = memberRepository.findById(deal.getMemberId()).orElseThrow();
        Product product = productRepository.findById(deal.getProductId()).orElseThrow();
        Deal registerDeal = deal.toEntity(member, product);

        return new DealResponseDto(dealRepository.save(registerDeal));
    }

    public void remove(Long dealId) {
        dealRepository.deleteById(dealId);
    }
}
