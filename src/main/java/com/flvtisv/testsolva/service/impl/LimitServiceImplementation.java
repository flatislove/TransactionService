package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.repository.LimitRepository;
import com.flvtisv.testsolva.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LimitServiceImplementation implements LimitService {

    private final LimitRepository limitRepository;

    @Autowired
    public LimitServiceImplementation(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Override
    public Optional<Limit> updateLimits(Limit limit, BigDecimal newLimitProduct, BigDecimal newLimitService) {
        return Optional.empty();
    }

    @Override
    public Optional<Limit> save(Limit limit) {
        return Optional.of(limitRepository.save(limit));
    }

    @Override
    public Limit getLimitById(long id){
        return limitRepository.getLimitById(id);
    }


    @Override
    public List<Limit> getAll() {
        return ((List<Limit>) limitRepository.findAll());
    }


    @Override
    public Limit getLimitByAccountIdAndType(long accountId, String type) {
        return limitRepository.getLimitByAccountIdAndType(accountId, type);
    }

}