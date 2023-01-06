package com.flvtisv.testsolva.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ExtendWith(MockitoExtension.class)
public class TwelveCurrencyServiceImplTest {

    @InjectMocks
    TwelveCurrencyServiceImpl twelveCurrencyService;

    @Test
    void getSumOfUsdValidKztTest(){
        String symbolFrom="KZT";
        String symbolTo="USD";
        BigDecimal sum=BigDecimal.valueOf(1000);
        BigDecimal ratio=BigDecimal.valueOf(2.5);
        BigDecimal result=twelveCurrencyService.getSumOfUsd(symbolFrom,symbolTo,sum,ratio);
        Assertions.assertEquals(BigDecimal.valueOf(400.0000).setScale(4, RoundingMode.CEILING),result);
    }

    @Test
    void getSumOfUsdValidRubTest(){
        String symbolFrom="RUB";
        String symbolTo="USD";
        BigDecimal sum=BigDecimal.valueOf(1000);
        BigDecimal ratio=BigDecimal.valueOf(2.5);
        BigDecimal result=twelveCurrencyService.getSumOfUsd(symbolFrom,symbolTo,sum,ratio);
        Assertions.assertEquals(BigDecimal.valueOf(400.0000).setScale(4, RoundingMode.CEILING),result);
    }

    @Test
    void getSumOfUsdValidsdTest(){
        String symbolFrom="USD";
        String symbolTo="USD";
        BigDecimal sum=BigDecimal.valueOf(1000);
        BigDecimal ratio=BigDecimal.valueOf(2.5);
        BigDecimal result=twelveCurrencyService.getSumOfUsd(symbolFrom,symbolTo,sum,ratio);
        Assertions.assertEquals(BigDecimal.valueOf(1000.0000).setScale(4, RoundingMode.CEILING),result);
    }

    @Test
    void getSumOfUsdInvalidTest(){
        String symbolFrom="BYN";
        String symbolTo="USD";
        BigDecimal sum=BigDecimal.valueOf(1000);
        BigDecimal ratio=BigDecimal.valueOf(2.5);
        BigDecimal result=twelveCurrencyService.getSumOfUsd(symbolFrom,symbolTo,sum,ratio);
        Assertions.assertEquals(BigDecimal.valueOf(-1),result);
    }
}
