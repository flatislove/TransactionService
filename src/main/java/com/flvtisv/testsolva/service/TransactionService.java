package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.controllersGraphql.dto.TransactionExceeded;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionInput;
import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> save(Transaction transaction);

    BigDecimal getSumTransactionsById(long id);

    Iterable<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId);

    Optional<Transaction> getById(long id);

    List<TransactionView> getListTransactionsViewFromRepository();

    TransactionView getTransactionViewFromTransaction(Transaction transaction);

    List<TransactionExceeded> getTransactionExceededFromTransaction(Account account);

    Transaction getTransactionAttributes(TransactionInput transactionInput, Account account, Limit limit);

}
