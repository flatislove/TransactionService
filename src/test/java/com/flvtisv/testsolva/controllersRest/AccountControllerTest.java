package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Account;
import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.dto.AccountAdd;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    AccountService accountService;

    @Mock
    LimitService limitService;

    @InjectMocks
    AccountController controller;

    @Test
    void newAccountValidTest(){
        AccountAdd accountAdd = new AccountAdd(2L,"3333333333",BigDecimal.valueOf(230000));
        var responseEntity = controller.newAccount(accountAdd, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        System.out.println(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON,responseEntity.getHeaders().getContentType());
        if (responseEntity.getBody() instanceof Account account){
            Assertions.assertEquals(accountAdd.getOwnerId(),account.getOwnerId());
            Assertions.assertEquals(accountAdd.getBalance(),account.getBalance());
            Assertions.assertEquals(accountAdd.getNumber(),account.getNumber());
        } else {
            Assertions.assertInstanceOf(Limit.class, responseEntity.getBody());
        }
    }


    @Test
    void addInvalidTypeNullAccountTest(){
        AccountAdd account = new AccountAdd(3L,null,BigDecimal.valueOf(200000));
        var responseEntity = this.controller.newAccount(account, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    void addInvalidBalanceTest(){
        AccountAdd account = new AccountAdd(13L,"3334445556",null);
        var responseEntity = this.controller.newAccount(account, UriComponentsBuilder.fromUriString("http:localhost:8080"));
        System.out.println(responseEntity.toString());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }
}
