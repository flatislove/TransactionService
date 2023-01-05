package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.service.AccountService;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class AccountControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @AfterEach
    void tearDown() {
        this.accountService.getAll().clear();
    }

    @Test
    void getAllAccountsEmptyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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
    void deleteValidTest() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/rest/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "ownerId": 6,
                               "number": "2222222232",
                               "balance": 2300000.00
                           }
                        """);
        this.mockMvc.perform(requestBuilder);

        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/accounts/2222222232")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteInvalidTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/accounts/2222222233")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getAllAccountsValidTest() throws Exception{
        var requestBuilderAddOne = MockMvcRequestBuilders.post("/rest/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "ownerId": 5,
                               "number": "2222222237",
                               "balance": 2300000.00
                           }
                        """);
        this.mockMvc.perform(requestBuilderAddOne);
        var requestBuilderAddTwo = MockMvcRequestBuilders.post("/rest/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "ownerId": 6,
                               "number": "2222222238",
                               "balance": 2300000.00
                           }
                        """);
        this.mockMvc.perform(requestBuilderAddTwo);
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/accounts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ownerId", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].number", Matchers.is("2222222237")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance", Matchers.is(2300000.00)))

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].ownerId", Matchers.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].number", Matchers.is("2222222238")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].balance", Matchers.is(2300000.00)));
    }
}