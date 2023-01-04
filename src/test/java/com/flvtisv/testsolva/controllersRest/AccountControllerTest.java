package com.flvtisv.testsolva.controllersRest;

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

//    @Test
//    void getCurrenciesReturnsValidResponseLimits(){
//        var currencies = List.of(new Currency("USD/KZT", BigDecimal.valueOf(73.75) ,"2022-12-30"),
//                new Currency("USD/RUB", BigDecimal.valueOf(462.81) ,"2022-12-30"));
//
//        Mockito.doReturn(currencies).when(this.currencyRepository).getAll();
//
//        var response = this.controller.getCurrencies();
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
//        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
//        Assertions.assertEquals(currencies, response.getBody());
//    }

    @Test
    void addValidAccountTest(){

        AccountAdd account = new AccountAdd(20L,"2221113339",BigDecimal.valueOf(2000000));
        var responseEntity = this.controller.newAccount(account, UriComponentsBuilder.fromUriString("http://localhost:8080"));
        System.out.println(responseEntity);
        Assertions.assertNotNull(responseEntity);
//        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());



//        Assertions.assertEquals(MediaType.APPLICATION_JSON,responseEntity.getHeaders().getContentType());
//        if (responseEntity.getBody() instanceof Account account1){
//            Assertions.assertEquals(account.getOwnerId(),account1.getOwnerId());
//            Assertions.assertEquals(account.getNumber(),account1.getNumber());
//            Assertions.assertEquals(account.getBalance(),account1.getBalance());
//
//            Assertions.assertEquals(URI.create("http://localhost:8080/rest/accounts"+account.getId()),
//                    responseEntity.getHeaders().getLocation());
//
//            Mockito.verify(this.accountService.save(account));
//        } else {
//            Assertions.assertInstanceOf(Account.class, responseEntity.getBody());
//        }
    }

    @Test
    void addInvalidTypeNullAccountTest(){
        AccountAdd account = new AccountAdd(3L,"3334445556",null);
        var responseEntity = this.controller.newAccount(account, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    void addInvalidBalanceTest(){
        AccountAdd account = new AccountAdd(13L,"3334445556",null);
        var responseEntity = this.controller.newAccount(account, UriComponentsBuilder.fromUriString("http:localhost:8080"));
        System.out.println(responseEntity.toString());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
//        Mockito.verifyNoInteractions(accountService);
    }
}
