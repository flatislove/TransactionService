package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Operation(summary = "Getting all accounts and information about them", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Accounts not found")})
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = service.getAll();
        return accounts == null || accounts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Operation(summary = "Adding a new account", responses = {
            @ApiResponse(responseCode = "201", description = "Account created"),
            @ApiResponse(responseCode = "400", description = "Incorrect number or account exist")})
    @PostMapping
    public ResponseEntity<?> newAccount(@RequestBody Account account) {
        if (account.getNumber().length() != 10 || service.isAccountExist(account.getNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Account> account1 = service.save(new Account(account.getOwnerId(), account.getNumber(), account.getBalance()));
        if (account1.isPresent()) {
            limitService.save(new Limit(account1.get(), BigDecimal.ZERO, limitService.getFormatDate(), ExpensesType.SERVICE.name()));
            limitService.save(new Limit(account1.get(), BigDecimal.ZERO, limitService.getFormatDate(), ExpensesType.PRODUCT.name()));
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(account1, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleting an account by number", responses = {
            @ApiResponse(responseCode = "204", description = "Successful deleted"),
            @ApiResponse(responseCode = "400", description = "Incorrect number or account not exist")})
    @DeleteMapping(value = "/{number}")
    public ResponseEntity<?> delete(
            @Parameter(description = "account number for delete")
            @PathVariable String number) {
        Optional<Account> account = service.getAccountByNumber(number);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.delete(account.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
