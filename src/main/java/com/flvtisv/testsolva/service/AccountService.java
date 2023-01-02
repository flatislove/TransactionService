package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> save(Account account);

    void delete(Account account);

    Optional<Account> findById(Long id);

    Optional<Account> getAccountByNumber(String number);

    List<Account> getAll();

    boolean isAccountExist(String number);
}