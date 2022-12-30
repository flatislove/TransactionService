package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/accounts")
@Tag(name = "Accounts", description = "Controller for displaying and adding customer accounts")
public class AccountController {

    private final AccountService service;
    private final LimitService limitService;

    @Autowired
    public AccountController(AccountService service, LimitService limitService) {
        this.service = service;
        this.limitService = limitService;
    }

    @Operation(summary = "Getting all accounts and information about them")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = service.getAll();
        return accounts == null || accounts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Operation(summary = "Adding a new account")
    @PostMapping
    public ResponseEntity<?> newAccount(@RequestBody Account account) {
        if (account.getNumber().length() != 10 || service.isAccountExist(account.getNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Account> account1 = service.save(new Account(account.getOwnerId(), account.getNumber(), account.getBalance()));
        if (account1.isPresent()) {
            String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            Limit newLimitProduct = new Limit(account1.get(), BigDecimal.ZERO, date, ExpensesType.PRODUCT.name());
            Limit newLimitService = new Limit(account1.get(), BigDecimal.ZERO, date, ExpensesType.SERVICE.name());
            limitService.save(newLimitService);
            limitService.save(newLimitProduct);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(account1, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleting an account by number")
    @DeleteMapping(value = "/{number}")
    public ResponseEntity<?> delete(@PathVariable String number) {
        Optional<Account> account = service.getAccountByNumber(number);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
