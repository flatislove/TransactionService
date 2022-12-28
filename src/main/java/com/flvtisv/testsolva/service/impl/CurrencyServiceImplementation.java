package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.repository.CurrencyRepository;
import com.flvtisv.testsolva.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImplementation implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyServiceImplementation(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Optional<Currency> save(Currency currency) {
        return Optional.of(currencyRepository.save(currency));
    }


    @Override
    public List<Currency> getAll() {
        return (List<Currency>) currencyRepository.findAll();
    }


    @Override
    public Currency getRatioBySymbol(String currency) {
        return currencyRepository.getFirstBySymbolOrderByDate(currency);
    }
}
