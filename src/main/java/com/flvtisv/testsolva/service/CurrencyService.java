package com.flvtisv.testsolva.service;

import com.flvtisv.testsolva.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {


    Optional<Currency> save(Currency currency);

    List<Currency> getAll();

    Currency getRatioBySymbol(String currency);
}
