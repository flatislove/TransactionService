package com.flvtisv.testsolva.controllers;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.entity.enums.CurrencyEnum;
import com.flvtisv.testsolva.service.*;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class TransactionController {

    private final TransactionService service;
    private final LimitService limitService;
    private final AccountService accountService;
    private final TwelveCurrencyService twelveCurrencyService;
    private final CurrencyService currencyService;

    @Autowired
    public TransactionController(TransactionService service, LimitService limitService, AccountService accountService, TwelveCurrencyService twelveCurrencyService, CurrencyService currencyService) {
        this.service = service;
        this.limitService = limitService;
        this.accountService = accountService;
        this.twelveCurrencyService = twelveCurrencyService;
        this.currencyService = currencyService;
    }

    @GetMapping("/transactions/getall")
    public List<Transaction> getAllCountries() {
        return service.getAll();
    }

    @SneakyThrows
    @PostMapping("/transactions/add")
    Optional<Transaction> newTransaction(@RequestBody Transaction transaction) {
        BigDecimal ratio = currencyService.getRatioBySymbol(CurrencyEnum.USD.name() + "/" + transaction.getCurrency()).getRate();
        BigDecimal newCount = twelveCurrencyService.getSumOfUsd(transaction.getCurrency(), CurrencyEnum.USD.name(), transaction.getSumOfMoney(), ratio);
        Optional<Account> account = Optional.ofNullable(accountService.getAccountByOwnerId(transaction.getAccount().getId()));
        Account account1 = null;
        account1 = account.get();
        account1.setBalance(account1.getBalance().subtract(newCount));
        Limit limit = limitService.getLimitByAccountIdAndType(account1.getOwnerId(), transaction.getType());
        transaction.setLimitId(limit.getId());
        BigDecimal sumTransactions = service.getSumTransactionsById(account1.getOwnerId());
//        System.out.println(sumTransactions + "<----- sum trans");
//        System.out.println(limit.getLimit() + "<---- limit");
//        System.out.println(newCount + "<------ new sum");
        int compareResult = limit.getLimit().compareTo((newCount.add(sumTransactions)));
        transaction.setStatusFlag(compareResult < 0);
        transaction.setCurrency(CurrencyEnum.USD.name());
        transaction.setSumOfMoney(newCount);
        transaction.setDate(new Date());
        return service.save(transaction);
    }

//    @GetMapping("/transactions/limitexceeding/{id}")
//    public List<Transaction> getAllExceedingTransactionsById(@PathVariable long id) {
//        return service.getTransactionsByAccountIdAndStatusFlagTrue(id);
//    }

    @GetMapping("/transactions/exceeded/{id}")
    public List<Map<String, Object>> getAllExceedingTransactions(@PathVariable long id) {
        List<Map<String, Object>> objects = new ArrayList<>();
        List<Transaction> transactions = service.getTransactionsByAccountIdAndStatusFlagTrue(id);
        for (Transaction transaction : transactions) {
            objects.add(service.getExceededTransactionForAnswer(accountService.getAccountByOwnerId(transaction.getAccount().getId()), transaction, limitService.getLimitById(transaction.getLimitId())));
        }
        return objects;
    }
}
