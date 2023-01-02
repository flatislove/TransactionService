package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void shouldReturnIsAccountExist(){
        String number = "1212121212";
        List<Account> accounts = getAccounts();
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);
        boolean isExistFirst=accountService.isAccountExist(number);
        Assertions.assertTrue(isExistFirst);
        String number2 = "2222221212";
        boolean isExistSecond=accountService.isAccountExist(number2);
        Assertions.assertFalse(isExistSecond);
    }

    private List<Account> getAccounts(){
        Account firstAcc = new Account(1,"1212121212", BigDecimal.valueOf(20000));
        Account secondAcc = new Account(1,"1111111111", BigDecimal.valueOf(30000));
        Account thirdAcc = new Account(2,"2222222222", BigDecimal.valueOf(40000));
        Account firthAcc = new Account(3,"3333333333", BigDecimal.valueOf(60000));
        return List.of(firstAcc,secondAcc,thirdAcc,firthAcc);
    }
}
