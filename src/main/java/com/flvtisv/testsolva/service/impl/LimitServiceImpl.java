package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.repository.LimitRepository;
import com.flvtisv.testsolva.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;

    @Autowired
    public LimitServiceImpl(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @Override
    public Optional<Limit> save(Limit limit) {
        return Optional.of(limitRepository.save(limit));
    }

    @Override
    public Limit getLimitById(long id) {
        return limitRepository.getLimitById(id);
    }

    @Override
    public List<Limit> getAll() {
        return ((List<Limit>) limitRepository.findAll());
    }

    @Override
    public Optional<Limit> getLimitByAccountIdAndType(long accountId, String type) {
        return limitRepository.getLimitByAccountIdAndType(accountId, type);
    }

    @Override
    public List<Limit> getAllLimitsByNumber(String number) {
        return limitRepository.getAllByAccount_Number(number);
    }
}