package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Limit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LimitService {

    Optional<Limit> updateLimits(Limit limit, BigDecimal newLimitProduct, BigDecimal newLimitService);

    Optional<Limit> save(Limit limit);

    Limit getLimitById(long id);

    List<Limit> getAll();

    Optional<Limit> getLimitByAccountIdAndType(long accountId, String type);

}