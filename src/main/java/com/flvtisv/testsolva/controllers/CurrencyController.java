package com.flvtisv.testsolva.controllers;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {

    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping("/currency/getall")
    public List<Currency> getCurrency() {
        return service.getAll();
    }
}
