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
                        new Currency(1,"USD/RUB", BigDecimal.valueOf(73.75) ,"2022-12-30"),
                        new Currency(2,"USD/KZT", BigDecimal.valueOf(462.81) ,"2022-12-30")));

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                    {
                                        "id": 1,
                                        "symbol": "USD/RUB",
                                        "rate": 73.75,
                                        "date": "2022-12-30"
                                    },
                                    {
                                        "id": 2,
                                        "symbol": "USD/KZT",
                                        "rate": 462.81,
                                        "date": "2022-12-30"
                                    }
                                ]
                                """)
                );
    }
}
