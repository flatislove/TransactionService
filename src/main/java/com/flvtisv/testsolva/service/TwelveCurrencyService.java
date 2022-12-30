package com.flvtisv.testsolva.service;

import java.math.BigDecimal;

public interface TwelveCurrencyService {
    BigDecimal getSumOfUsd(String symbolFrom, String symbolTo, BigDecimal sum, BigDecimal ratio);

    public void getRatioUsdKzt();

    public void getRatioUsdRub();
}
