package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.repository.AccountRepository;
import com.flvtisv.testsolva.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> updateLimits(Account account, BigDecimal newLimitProduct, BigDecimal newLimitService) {
        return null;
    }

    @Override
    public Optional<Account> save(Account account) {
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByNumber(String number) {
        System.out.println(number + " <- from impl number");
        return Optional.ofNullable(accountRepository.getAccountByNumber(number));
    }

    @Override
    public List<Account> getAll() {
        return ((List<Account>) accountRepository.findAll());
    }

    @Override
    public Account getAccountByOwnerId(long ownerId) {
        return accountRepository.getAccountByOwnerId(ownerId);
    }

}
