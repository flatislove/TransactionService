package com.flvtisv.testsolva.controllersGraphql;

import com.flvtisv.testsolva.controllersGraphql.dto.TransactionExceeded;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionInput;
import com.flvtisv.testsolva.controllersGraphql.dto.TransactionView;
import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.Transaction;
import com.flvtisv.testsolva.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Tag(name = "Transactions", description = "Controller for adding transactions and displaying exceeded and all transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final LimitService limitService;

    public TransactionController(TransactionService transactionService, AccountService accountService, LimitService limitService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.limitService = limitService;
    }

    @QueryMapping
    @Operation(summary = "Displaying all transactions")
    List<TransactionView> transactions() {
        return transactionService.getListTransactionsViewFromRepository();
    }

    @QueryMapping
    @Operation(summary = "Getting transaction by Id")
    Optional<TransactionView> transactionById(@Argument Long id) {
        Optional<Transaction> transaction = Optional.ofNullable(transactionService.getById(id).orElseThrow(() ->
                new IllegalArgumentException("transaction not found")));
        Transaction transact=null;
        if (transaction.isPresent()){
             transact = transaction.get();
        }
        return Optional.ofNullable(transactionService.getTransactionViewFromTransaction(transact));
    }

    @QueryMapping
    @Operation(summary = "Displaying exceeded transactions")
    Iterable<TransactionExceeded> exceededTransactions(@Argument Long id) {
        Optional<Account> account = Optional.ofNullable(accountService.findById(id).orElseThrow(() ->
                new IllegalArgumentException("account not found")));
        Account account1=null;
        if (account.isPresent()){
            account1=account.get();
        }
        return transactionService.getTransactionExceededFromTransaction(account1);
    }

    @MutationMapping
    @Operation(summary = "Adding transaction")
    TransactionInput addTransaction(@Argument TransactionInput transaction) {
        if (transaction.account_from().length() != 10 || transaction.account_to().length() != 10) {
            throw new IllegalArgumentException("account number \"from\" or \"to\" is wrong");
        }
        if (transaction.currency_shortname() == null || (!transaction.currency_shortname().equals("KZT") && !transaction.currency_shortname().equals("RUB"))) {
            throw new IllegalArgumentException("currency argument is wrong");
        }
        Account account = accountService.getAccountByNumber(transaction.account_from()).orElseThrow(() -> new IllegalArgumentException("account not found"));
        Limit limit = limitService.getLimitByAccountIdAndType(account.getId(), transaction.expense_category()).orElseThrow(() -> new IllegalArgumentException("limit not found"));
        transactionService.save(transactionService.getTransactionAttributes(transaction,account,limit));
        return transaction;
    }
}