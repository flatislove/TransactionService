package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

//ok
@ExtendWith(MockitoExtension.class)
public class CurrencyControllerTest {
    @Mock
    CurrencyService currencyRepository;

    @InjectMocks
    CurrencyController controller;

    @Test
    void getCurrenciesReturnsValidResponseCurrencies(){
        var currencies = List.of(new Currency("USD/KZT", BigDecimal.valueOf(73.75) ,"2022-12-30"),
                new Currency("USD/RUB", BigDecimal.valueOf(462.81) ,"2022-12-30"));

        Mockito.doReturn(currencies).when(this.currencyRepository).getAll();

        var response = this.controller.getCurrencies();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
        Assertions.assertEquals(currencies, response.getBody());
    }

    @Test
    void getCurrenciesReturnsEmptyResponseCurrencies(){
        Mockito.doReturn(null).when(this.currencyRepository).getAll();
        var response = this.controller.getCurrencies();
        Assertions.assertEquals(currencyRepository.getAll(),response.getBody());
    }
}
