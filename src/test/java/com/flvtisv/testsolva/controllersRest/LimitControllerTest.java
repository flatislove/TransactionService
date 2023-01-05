package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Limit;
import com.flvtisv.testsolva.entity.dto.LimitAdd;
import com.flvtisv.testsolva.service.AccountService;
import com.flvtisv.testsolva.service.LimitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class LimitControllerTest {

    @Mock
    LimitService limitService;

    @Mock
    AccountService accountService;

    @InjectMocks
    LimitController controller;


//    @Test
//    void getCurrenciesReturnsValidResponseLimits(){
//        var currencies = List.of(new Currency("USD/KZT", BigDecimal.valueOf(73.75) ,"2022-12-30"),
//                new Currency("USD/RUB", BigDecimal.valueOf(462.81) ,"2022-12-30"));
//
//        Mockito.doReturn(currencies).when(this.currencyService).getAll();
//
//        var response = this.controller.getCurrencies();
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
//        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
//        Assertions.assertEquals(currencies, response.getBody());
//    }

    @Test
    void addLimitValidTest(){
        LimitAdd limit = new LimitAdd(2L,BigDecimal.valueOf(2000),"SERVICE");
        var responseEntity = this.controller.addLimit(limit, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON,responseEntity.getHeaders().getContentType());
        if (responseEntity.getBody() instanceof LimitAdd limitAdd){
            Assertions.assertEquals(limit.getAccountId(),limitAdd.getAccountId());
            Assertions.assertEquals(limit.getLimit(),limitAdd.getLimit());
            Assertions.assertEquals(limit.getType(),limitAdd.getType());

//            Assertions.assertEquals(URI.create("http://localhost:8080/rest/limits"+limitAdd.),
//                    responseEntity.getHeaders().getLocation()); //проверить что выводит
        } else {
            Assertions.assertInstanceOf(Limit.class, responseEntity.getBody());
        }
    }

    @Test
    void addLimitInvalidTypeNullLimitTest(){
        LimitAdd limit = new LimitAdd(2L,BigDecimal.valueOf(2000),null);
        var responseEntity = this.controller.addLimit(limit, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    void addLimitInvalidTypeOtherLimitTest(){
        LimitAdd limit = new LimitAdd(2L,BigDecimal.valueOf(2000),"OTHER");
        var responseEntity = this.controller.addLimit(limit, UriComponentsBuilder.fromUriString("http:localhost:8080"));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
        Mockito.verifyNoInteractions(limitService);
    }
}
