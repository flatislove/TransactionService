package com.flvtisv.testsolva.controllersGraphql;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.entity.enums.CurrencyEnum;
import com.flvtisv.testsolva.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Tag(name = "Transactions", description = "Controller for adding transactions and displaying exceeded and all transactions")
public class TransactionControllerGraphql {
    private final TransactionService transactionService;
    private final LimitService limitService;
    private final TwelveCurrencyService twelveCurrencyService;
    private final CurrencyService currencyService;

    private final AccountService accountService;

    public TransactionControllerGraphql(TransactionService transactionService, AccountService accountService,
                                        LimitService limitService, TwelveCurrencyService twelveCurrencyService,
                                        CurrencyService currencyService) {
        this.transactionService = transactionService;
        this.limitService = limitService;
        this.twelveCurrencyService = twelveCurrencyService;
        this.currencyService = currencyService;
        this.accountService = accountService;
    }

    @QueryMapping
    @Operation(summary = "Displaying all transactions")
    List<TransactionView> transactions() {
        Iterable<Transaction> transactions = transactionService.getAll();
        List<TransactionView> transactionViews = new ArrayList<>();
        for (Transaction tr : transactions) {
            transactionViews.add(new TransactionView((long) tr.getId(), tr.getAccountTo(), tr.getType(), tr.getDate(),
                    tr.isStatusFlag(), tr.getLimitId(), tr.getCurrency(), tr.getSumOfMoney()));
        }
        return transactionViews;
    }

    @QueryMapping
    @Operation(summary = "Getting transaction by Id")
    Optional<TransactionView> transactionById(@Argument Long id) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionService.getById(id).orElseThrow(() ->
                new IllegalArgumentException("transaction not found")));
        TransactionView tw = null;
        if (transaction.isPresent()) {
            Transaction tr = transaction.get();
            tw = new TransactionView((long) tr.getId(), tr.getAccountTo(), tr.getType(), tr.getDate(),
                    tr.isStatusFlag(), tr.getLimitId(), tr.getCurrency(), tr.getSumOfMoney());
        }
        return Optional.ofNullable(tw);
    }

    @QueryMapping
    @Operation(summary = "Displaying exceeded transactions")
    Iterable<TransactionExceeded> exceededTransactions(@Argument Long id) {
        Optional<Account> account = Optional.ofNullable(accountService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("account not found")));
        Iterable<Transaction> transactions;
        List<TransactionExceeded> transactionsExceeded = new ArrayList<>();
        if (account.isPresent()) {
            transactions = transactionService.getTransactionsByAccountIdAndStatusFlagTrue(account.get().getId());
            for (Transaction transaction : transactions) {
                Limit limit = limitService.getLimitById(transaction.getLimitId());
                transactionsExceeded.add(new TransactionExceeded((long) transaction.getId(), account.get().getNumber(),
                        transaction.getAccountTo(), transaction.getCurrency(), transaction.getSumOfMoney(), transaction.getType(),
                        transaction.getDate(), limit.getLimit(), limit.getDateLimit(), transaction.getCurrency()));
            }
        }
        return transactionsExceeded;
    }

    @MutationMapping
    @Operation(summary = "Adding transaction")
    TransactionInput addTransaction(@Argument TransactionInput transaction) {
        if (transaction.account_from().length() != 10 || transaction.account_to.length() != 10) {
            throw new IllegalArgumentException("account number \"from\" or \"to\" is wrong");
        }
        if (transaction.currency_shortname() == null || (!transaction.currency_shortname().equals("KZT") && !transaction.currency_shortname().equals("RUB"))) {
            throw new IllegalArgumentException("currency argument is wrong");
        }
        String currencyPair = CurrencyEnum.USD.name() + "/" + transaction.currency_shortname();
        Currency currency = currencyService.getRatioBySymbol(currencyPair);
        BigDecimal ratio;
        if (currency == null || currency.getRate() == null) {
            if (transaction.currency_shortname().equals("KZT")) {
                twelveCurrencyService.getRatioUsdKzt();
            } else {
                twelveCurrencyService.getRatioUsdRub();
            }
        }
        ratio = currencyService.getRatioBySymbol(CurrencyEnum.USD.name() + "/" + transaction.currency_shortname()).getRate();
        BigDecimal newCount = twelveCurrencyService.getSumOfUsd(transaction.currency_shortname(), CurrencyEnum.USD.name(), transaction.sum(), ratio);
        Account account = accountService.getAccountByNumber(transaction.account_from()).orElseThrow(() -> new IllegalArgumentException("account not found"));
        account.setBalance(account.getBalance().subtract(newCount));
        Limit limit = limitService.getLimitByAccountIdAndType(account.getId(), transaction.expense_category()).orElseThrow(() -> new IllegalArgumentException("limit not found"));
        BigDecimal sumTransactions = transactionService.getSumTransactionsById(account.getId());
        int compareResult = limit.getLimit().compareTo((newCount.add(sumTransactions)));
        boolean exceeded = compareResult < 0;
        Transaction transact = new Transaction(account, transaction.account_to(), transaction.expense_category(),
                exceeded, limit.getId(), CurrencyEnum.USD.name(), newCount);
        transactionService.save(transact);
        return transaction;
    }

    record TransactionInput(String account_from, String account_to, String currency_shortname, BigDecimal sum,
                            String expense_category) {
    }

    record TransactionExceeded(Long id, String account_from, String account_to, String currency_shortname,
                               BigDecimal sum, String expense_category, String datetime, BigDecimal limit_sum,
                               String limit_datetime, String limit_currency_shortname) {
    }

    record TransactionView(Long id, String account_to, String expense_category, String datetime, Boolean limit_exceeded,
                           Long limit_id, String currency_shortname, BigDecimal sum) {
    }
}