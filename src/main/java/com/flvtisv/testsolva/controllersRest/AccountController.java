package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.dto.AccountAdd;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(accounts);
    }

    @Operation(summary = "Adding a new account", responses = {
            @ApiResponse(responseCode = "201", description = "Account created"),
            @ApiResponse(responseCode = "400", description = "Incorrect number or account exist")})
    @PostMapping
    public ResponseEntity<?> newAccount(@RequestBody AccountAdd account, UriComponentsBuilder uriComponentsBuilder) {
        if (account.getNumber()==null || account.getNumber().length() != 10 || service.isAccountExist(account.getNumber())) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).build();
        }
        Optional<Account> account1 = service.save(new Account(account.getOwnerId(), account.getNumber(), account.getBalance()));
        if (account1.isPresent()) {
            limitService.save(new Limit(account1.get(), BigDecimal.ZERO, limitService.getFormatDate(), ExpensesType.SERVICE.name()));
            limitService.save(new Limit(account1.get(), BigDecimal.ZERO, limitService.getFormatDate(), ExpensesType.PRODUCT.name()));
        } else return ResponseEntity.badRequest().build();
        return ResponseEntity.created(uriComponentsBuilder
                .path("/rest/accounts/{id}").build(Map.of("id",account1.get().getId())))
                .contentType(MediaType.APPLICATION_JSON).body(account1);
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
            return ResponseEntity.badRequest().build();
        }
        service.delete(account.get());
        return ResponseEntity.noContent().build();
    }
}
