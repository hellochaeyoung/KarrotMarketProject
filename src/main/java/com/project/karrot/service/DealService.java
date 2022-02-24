package com.project.karrot.service;

import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.dto.MemberRequestDto;
import com.project.karrot.dto.ProductRequestDto;
import com.project.karrot.repository.DealRepository;

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
