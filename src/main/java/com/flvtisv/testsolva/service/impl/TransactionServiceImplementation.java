package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.repository.TransactionRepository;
import com.flvtisv.testsolva.service.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImplementation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Transaction> save(Transaction transaction) {
        return Optional.of(transactionRepository.save(transaction));
    }

    @Override
    public List<Transaction> getAll() {
        return ((List<Transaction>) transactionRepository.findAll());
    }

    @Override
    public BigDecimal getSumTransactionsById(long id) {
        BigDecimal sum = transactionRepository.getSumTransactionsById(id);
        if (sum != null) {
            return sum;
        }
        return new BigDecimal("0");
    }

    public List<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId) {
        return transactionRepository.getTransactionsByAccountIdAndStatusFlagTrue(accountId);
    }

    public Map<String,Object> getExceededTransactionForAnswer(Account account, Transaction transaction, Limit limit) {
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> map = new HashMap<>();
//        System.out.println(account+"<---- account");
//        System.out.println(transaction+"<----- transaction");
//        System.out.println(limit+"<---- limit");
        map.put("account_from", account.getNumber());
        map.put("account_to", account.getNumber());
        map.put("currency_shortname", transaction.getCurrency());
        map.put("sum", transaction.getSumOfMoney());
        map.put("expense_category", transaction.getType());
        map.put("datetime", transaction.getDate());
        map.put("limit_sum", limit.getLimit());
        map.put("limit_datetime", limit.getDateLimit());
        map.put("limit_currency_shortname", transaction.getCurrency());
//        System.out.println(map+"<--------------- json");
        return map;
    }

//    public JSONObject getJsonObject(long id){
//        return transactionRepository.getJsonObject(id);
//    }
}
