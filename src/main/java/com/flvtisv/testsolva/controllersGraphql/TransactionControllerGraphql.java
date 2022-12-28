package com.flvtisv.testsolva.controllersGraphql;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.entity.enums.CurrencyEnum;
import com.flvtisv.testsolva.repository.AccountRepository;
import com.flvtisv.testsolva.repository.LimitRepository;
import com.flvtisv.testsolva.service.CurrencyService;
import com.flvtisv.testsolva.service.TransactionService;
import com.flvtisv.testsolva.service.TwelveCurrencyService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionControllerGraphql {
    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final LimitRepository limitRepository;
    private final TwelveCurrencyService twelveCurrencyService;
    private final CurrencyService currencyService;

    public TransactionControllerGraphql(TransactionService transactionService, AccountRepository accountRepository, LimitRepository limitRepository, TwelveCurrencyService twelveCurrencyService, CurrencyService currencyService) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.limitRepository = limitRepository;
        this.twelveCurrencyService = twelveCurrencyService;
        this.currencyService = currencyService;
    }

    @QueryMapping
    Iterable<Transaction> transactions() {
        return transactionService.getAll();
    }

    @QueryMapping
    Optional<Transaction> transactionById(@Argument Long id) {
        return transactionService.getById(id);
    }

    @QueryMapping
    Iterable<TransactionExceeded> exceededTransactions(@Argument Long id) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("account not found")));
        Iterable<Transaction> transactions = transactionService.getTransactionsByAccountIdAndStatusFlagTrue(account.get().getId());
        List<TransactionExceeded> transactionsExceeded = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Limit limit = limitRepository.getLimitById(transaction.getLimitId());
            transactionsExceeded.add(new TransactionExceeded((long) transaction.getId(), account.get().getNumber(),
                    transaction.getAccountTo(), transaction.getCurrency(), transaction.getSumOfMoney(), transaction.getType(),
                    transaction.getDate().toString(), limit.getLimit(), limit.getDateLimit().toString(), transaction.getCurrency()));
        }
        return transactionsExceeded;
    }

    @MutationMapping
    Optional<Transaction> addTransaction(@Argument TransactionInput transaction) {
        BigDecimal ratio = currencyService.getRatioBySymbol(CurrencyEnum.USD.name() + "/" + transaction.currency()).getRate();
        BigDecimal newCount = twelveCurrencyService.getSumOfUsd(transaction.currency(), CurrencyEnum.USD.name(), transaction.sumOfMoney(), ratio);
        Account account = accountRepository.findById(transaction.accountId()).orElseThrow(() -> new IllegalArgumentException("account not found"));
        account.setBalance(account.getBalance().subtract(newCount));
        Limit limit = limitRepository.getLimitByAccountIdAndType(transaction.accountId(), transaction.type()).orElseThrow(() -> new IllegalArgumentException("limit not found"));
        BigDecimal sumTransactions = transactionService.getSumTransactionsById(account.getId());
        int compareResult = limit.getLimit().compareTo((newCount.add(sumTransactions)));
        boolean exceeded = compareResult < 0;
        Transaction transact = new Transaction(account, transaction.accountTo(), transaction.type(), new Date(), exceeded, limit.getId(), CurrencyEnum.USD.name(), newCount);
        return transactionService.save(transact);
    }

    record TransactionInput(Long accountId, String accountTo, String type, String currency, BigDecimal sumOfMoney) {
    }

    record TransactionExceeded(Long id, String account_from, String account_to, String currency_shortname, BigDecimal sum,
            String expense_category, String datetime, BigDecimal limit_sum, String limit_datetime, String limit_currency_shortname) {
    }
}