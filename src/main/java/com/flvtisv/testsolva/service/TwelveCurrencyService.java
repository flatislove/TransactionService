package com.flvtisv.testsolva.service;

import java.math.BigDecimal;

public interface TwelveCurrencyService {
    BigDecimal getSumOfUsd(String symbolFrom, String symbolTo, BigDecimal sum, BigDecimal ratio);

    void getRatioUsdKzt();

    void getRatioUsdRub();
}
