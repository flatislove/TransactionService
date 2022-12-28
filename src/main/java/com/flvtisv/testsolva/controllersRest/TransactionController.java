//package com.flvtisv.testsolva.controllersRest;
//
//import com.flvtisv.testsolva.entity.Transaction;
//import com.flvtisv.testsolva.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class TransactionController {
//
//    private final TransactionService service;
//    private final LimitService limitService;
//    private final AccountService accountService;
//    private final TwelveCurrencyService twelveCurrencyService;
//    private final CurrencyService currencyService;
//
//    @Autowired
//    public TransactionController(TransactionService service, LimitService limitService, AccountService accountService, TwelveCurrencyService twelveCurrencyService, CurrencyService currencyService) {
//        this.service = service;
//        this.limitService = limitService;
//        this.accountService = accountService;
//        this.twelveCurrencyService = twelveCurrencyService;
//        this.currencyService = currencyService;
//    }
//
//    @GetMapping("/transactions/getall")
//    public List<Transaction> getAllCountries() {
//        return service.getAll();
//    }
//
//
//    @GetMapping("/transactions/exceeded/{id}")
//    public List<Map<String, Object>> getAllExceedingTransactions(@PathVariable long id) {
//        List<Map<String, Object>> objects = new ArrayList<>();
//        List<Transaction> transactions = service.getTransactionsByAccountIdAndStatusFlagTrue(id);
//        for (Transaction transaction : transactions) {
//            objects.add(service.getExceededTransactionForAnswer(accountService.getAccountByOwnerId(transaction.getAccount().getId()), transaction, limitService.getLimitById(transaction.getLimitId())));
//        }
//        return objects;
//    }
//}
