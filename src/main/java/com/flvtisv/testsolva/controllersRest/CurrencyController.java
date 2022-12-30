package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rest/currency")
@RestController
@Tag(name = "Currencies", description = "Controller for working with exchange rates")
public class CurrencyController {

    private final CurrencyService service;

    @Autowired
    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Getting all currencies")
    public ResponseEntity<List<Currency>> getCurrency() {
        List<Currency> currencies = service.getAll();
        return currencies == null || currencies.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(currencies, HttpStatus.OK);
    }
}
