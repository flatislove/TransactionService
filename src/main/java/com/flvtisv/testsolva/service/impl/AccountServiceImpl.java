package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.repository.AccountRepository;
import com.flvtisv.testsolva.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> save(Account account) {
        return Optional.of(accountRepository.save(account));
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByNumber(String number) {
        return accountRepository.getAccountByNumber(number);
    }

    @Override
    public List<Account> getAll() {
        return ((List<Account>) accountRepository.findAll());
    }

    @Override
    public boolean isAccountExist(String number) {
        List<Account> accounts = (List<Account>) accountRepository.findAll();
        for (Account account : accounts) {
            if (account.getNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getFormatDate(){
        String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
