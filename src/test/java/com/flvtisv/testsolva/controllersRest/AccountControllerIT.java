package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//ok
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class AccountControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @AfterEach
    void tearDown(){
        this.accountService.getAll().clear();
    }

    @Test
    void addAccountValidTest() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/rest/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "ownerId": 5,
                               "number": "2222222238",
                               "balance": 2300000.00
                           }
                        """);
        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        header().exists(HttpHeaders.LOCATION),
                        content().json("""
                                {
                                    "ownerId": 5,
                                    "number": "2222222238",
                                    "balance": 2300000.00
                                }
                                """),
                        jsonPath("$.id").exists()
                );
        Assertions.assertEquals(1, this.accountService.getAll().size());
        final var account = this.accountService.getAll().get(0);
        Assertions.assertEquals("2222222238", account.getNumber());
        Assertions.assertEquals(BigDecimal.valueOf(2300000.00).setScale(2, RoundingMode.CEILING), account.getBalance());
    }

    @Test
    void addAccountInvalidTest() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/rest/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "ownerId": 5,
                               "number": null,
                               "balance": 2300000.00
                           }
                        """);
        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        header().doesNotExist(HttpHeaders.LOCATION)
                );
        Assertions.assertTrue(this.accountService.getAll().isEmpty());
    }
}

