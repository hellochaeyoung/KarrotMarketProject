package com.project.karrot.src.deal;

import com.project.karrot.src.product.dto.ProductRequestDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class DealService {

    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public Optional<Deal> find(Long dealId) {
        return dealRepository.findById(dealId);
    }

    public Optional<Deal> findByProduct(ProductRequestDto product) {

        return dealRepository.findByProductId(product.getProductId());
    }
    public Optional<List<Deal>> findByMember(Long memberId) {

        return dealRepository.findByMemberId(memberId);
    }

    public Deal register(Deal deal) {
        return dealRepository.save(deal);
    }

    public void remove(Deal deal) {
        dealRepository.delete(deal);
    }
}
