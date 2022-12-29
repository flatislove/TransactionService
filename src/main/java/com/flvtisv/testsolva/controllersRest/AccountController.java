package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/accounts")
//@Api("Controller for displaying and adding customer accounts")
public class AccountController {

    private final AccountService service;
    private final LimitService limitService;

    @Autowired
    public AccountController(AccountService service, LimitService limitService) {
        this.service = service;
        this.limitService = limitService;
    }

//    @ApiOperation("Getting all accounts and information about them")
    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAll();
    }

//    @ApiOperation("Adding a new account")
    @PostMapping
    Optional<Account> newAccount(@RequestBody Account account) {
        Optional<Account> account1 = service.save(new Account(account.getOwnerId(), account.getNumber(), account.getBalance()));
        if (account1.isPresent()) {
            String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            Limit newLimitProduct = new Limit(account1.get(), BigDecimal.ZERO, date, ExpensesType.PRODUCT.name());
            Limit newLimitService = new Limit(account1.get(), BigDecimal.ZERO, date, ExpensesType.SERVICE.name());
            limitService.save(newLimitService);
            limitService.save(newLimitProduct);
        }
        return account1;
    }
}
