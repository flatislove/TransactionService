package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> updateLimits(Account account, BigDecimal newLimitProduct, BigDecimal newLimitService);

    Optional<Account> save(Account account);

    Optional<Account> getById(Long id);

    Optional<Account> getAccountByNumber(String number);

    List<Account> getAll();

    Account getAccountByOwnerId(long ownerId);
}