package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.repository.TransactionRepository;
import com.flvtisv.testsolva.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

    public Iterable<Transaction> getTransactionsByAccountIdAndStatusFlagTrue(long accountId) {
        return transactionRepository.getTransactionsByAccountIdAndStatusFlagTrue(accountId);
    }

    public Optional<Transaction> getById(long id) {
        return transactionRepository.findById(id);
    }

}
