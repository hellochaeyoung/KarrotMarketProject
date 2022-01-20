package com.project.karrot.service;

import com.project.karrot.domain.Deal;
import com.project.karrot.domain.Member;
import com.project.karrot.domain.Product;
import com.project.karrot.repository.DealRepository;

import java.util.List;
import java.util.Optional;

public class DealService {

    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    public Optional<Deal> find(Long dealId) {
        return dealRepository.findById(dealId);
    }

    public Optional<Deal> findByProduct(Product product) {
        return dealRepository.findByProduct(product);
    }
    public Optional<List<Deal>> findByMember(Member member) {
        return dealRepository.findByMember(member);
    }

    public Deal register(Deal deal) {
        return dealRepository.save(deal);
    }

    public void remove(Deal deal) {
        dealRepository.delete(deal);
    }
}
