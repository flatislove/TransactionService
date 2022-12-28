package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.enums.ExpensesType;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {

    private final AccountService service;
    private final LimitService limitService;

    @Autowired
    public AccountController(AccountService service, LimitService limitService) {
        this.service = service;
        this.limitService = limitService;
    }

    @GetMapping("/account/getall")
    public List<Account> getAllAccounts() {
        return service.getAll();
    }

    @PostMapping("/account/add")
    Optional<Account> newAccount(@RequestBody Account account) {
        Optional<Account> account1 = service.save(new Account(account.getOwnerId(), account.getNumber(), account.getBalance()));
        if (account1.isPresent()) {
            Limit newLimitProduct = new Limit(account1.get(), BigDecimal.ZERO, new Date(), ExpensesType.PRODUCT.name());
            Limit newLimitService = new Limit(account1.get(), BigDecimal.ZERO, new Date(), ExpensesType.SERVICE.name());
            limitService.save(newLimitService);
            limitService.save(newLimitProduct);
        }
        return account1;
    }

//    @PutMapping("/account/updatelimits/{id}")
//    Optional<Account> updateLimits(@PathVariable long id, @RequestBody Account account) {
//        Optional<Account> accountForUpdate = service.getById(id);
//        if (accountForUpdate.isPresent()) {
//            Account account1 = accountForUpdate.get();
//            return service.save(account1);
//        } else {
//            return Optional.empty();
//        }
//    }
}
