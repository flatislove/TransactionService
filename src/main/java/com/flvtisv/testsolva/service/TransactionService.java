package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> save(Transaction transaction);

    List<Transaction> getAll();

    BigDecimal getSumTransactionsById(long id);

    Iterable<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId);

//    JSONObject getJsonObject(long id);

    Optional<Transaction> getById(long id);
}
