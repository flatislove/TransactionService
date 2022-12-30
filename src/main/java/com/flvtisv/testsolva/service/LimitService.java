package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Limit;

import java.util.List;
import java.util.Optional;

public interface LimitService {

    Optional<Limit> save(Limit limit);

    Limit getLimitById(long id);

    List<Limit> getAll();

    Optional<Limit> getLimitByAccountIdAndType(long accountId, String type);

    List<Limit> getAllLimitsByNumber(String number);
}