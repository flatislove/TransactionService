package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Getting all currencies", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Currency not found")})
    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies() {
        List<Currency> currencies = service.getAll();
        return currencies == null || currencies.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(currencies);
    }
}
