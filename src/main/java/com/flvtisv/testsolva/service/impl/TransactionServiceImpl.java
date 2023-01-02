package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.controllersGraphql.dto.TransactionExceeded;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionInput;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionView;
import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.entity.enums.CurrencyEnum;
import com.flvtisv.testsolva.repository.TransactionRepository;
import com.flvtisv.testsolva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final LimitService limitService;
    private final CurrencyService currencyService;
    private final TwelveCurrencyService twelveCurrencyService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, LimitService limitService, CurrencyService currencyService, TwelveCurrencyService twelveCurrencyService) {
        this.transactionRepository = transactionRepository;
        this.limitService = limitService;
        this.currencyService = currencyService;
        this.twelveCurrencyService = twelveCurrencyService;
    }

    @Override
    public Optional<Transaction> save(Transaction transaction) {
        return Optional.of(transactionRepository.save(transaction));
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

    public List<TransactionView> getListTransactionsViewFromRepository() {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        List<TransactionView> transactionViews = new ArrayList<>();
        for (Transaction tr : transactions) {
            transactionViews.add(new TransactionView((long) tr.getId(), tr.getAccountTo(), tr.getType(), tr.getDate(),
                    tr.isStatusFlag(), tr.getLimitId(), tr.getCurrency(), tr.getSumOfMoney()));
        }
        return transactionViews;
    }

    public TransactionView getTransactionViewFromTransaction(Transaction tr) {
        return new TransactionView((long) tr.getId(), tr.getAccountTo(), tr.getType(), tr.getDate(),
                tr.isStatusFlag(), tr.getLimitId(), tr.getCurrency(), tr.getSumOfMoney());
    }

    @Override
    public List<TransactionExceeded> getTransactionExceededFromTransaction(Account account) {
        Iterable<Transaction> transactions;
        List<TransactionExceeded> transactionsExceeded = new ArrayList<>();
        transactions = getTransactionsByAccountIdAndStatusFlagTrue(account.getId());
        for (Transaction transaction : transactions) {
            Limit limit = limitService.getLimitById(transaction.getLimitId());
            transactionsExceeded.add(new TransactionExceeded((long) transaction.getId(), account.getNumber(),
                    transaction.getAccountTo(), transaction.getCurrency(), transaction.getSumOfMoney(), transaction.getType(),
                    transaction.getDate(), limit.getLimit(), limit.getDateLimit(), transaction.getCurrency()));
        }
        return transactionsExceeded;
    }

    @Override
    public Transaction getTransactionAttributes(TransactionInput transactionInput, Account account, Limit limit){
        String currencyPair = CurrencyEnum.USD.name() + "/" + transactionInput.currency_shortname();
        Currency currency = currencyService.getRatioBySymbol(currencyPair);
        BigDecimal ratio;
        if (currency == null || currency.getRate() == null) {
            if (transactionInput.currency_shortname().equals("KZT")) {
                twelveCurrencyService.getRatioUsdKzt();
            } else {
                twelveCurrencyService.getRatioUsdRub();
            }
        }
        ratio = currencyService.getRatioBySymbol(CurrencyEnum.USD.name() + "/" + transactionInput.currency_shortname()).getRate();
        BigDecimal newCount = twelveCurrencyService.getSumOfUsd(transactionInput.currency_shortname(), CurrencyEnum.USD.name(), transactionInput.sum(), ratio);
        account.setBalance(account.getBalance().subtract(newCount));

        BigDecimal sumTransactions = getSumTransactionsById(account.getId());
        int compareResult = limit.getLimit().compareTo((newCount.add(sumTransactions)));
        boolean exceeded = compareResult < 0;
        return new Transaction(account, transactionInput.account_to(), transactionInput.expense_category(), limitService.getFormatDate(),
                exceeded, limit.getId(), CurrencyEnum.USD.name(), newCount);
    }
}
