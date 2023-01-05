package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.entity.Currency;
import com.flvtisv.testsolva.service.CurrencyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class CurrencyControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CurrencyService currencyService;


    @AfterEach
    void tearDown(){
        this.currencyService.getAll().clear();
    }

    @Test
    void getCurrenciesReturnsValidResponseCurrencies() throws Exception{
        var requestBuilder = MockMvcRequestBuilders.get("/rest/currency");
        this.currencyService.getAll()
                .addAll(List.of(
                        new Currency(1,"USD/KZT", BigDecimal.valueOf(465.76) ,"2023-01-04"),
                        new Currency(2,"USD/RUB", BigDecimal.valueOf(73.40) ,"2023-01-04")));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": 1,
                                        "symbol": "USD/KZT",
                                        "rate": 465.76,
                                        "date": "2023-01-04"
                                    },
                                    {
                                        "id": 2,
                                        "symbol": "USD/RUB",
                                        "rate": 73.40,
                                        "date": "2023-01-04"
                                    }
                                ]
                                """)
                );
    }

    @Test
    void getCurrenciesReturnsInvalidResponseCurrencies() throws Exception{
        var requestBuilder = MockMvcRequestBuilders.get("/rest/currency");
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }
}
