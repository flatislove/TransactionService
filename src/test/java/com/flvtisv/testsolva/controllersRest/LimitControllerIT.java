package com.flvtisv.testsolva.controllersRest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flvtisv.testsolva.entity.dto.LimitAdd;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;


@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class LimitControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    LimitController limitController;

    @Test
    void getLimitsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/rest/limits/2222222238")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(7)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].limit", Matchers.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateLimit", Matchers.is("2023-01-05 00:16:47+06")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is("SERVICE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].limit", Matchers.is(0.00)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateLimit", Matchers.is("2023-01-05 00:16:47+06")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", Matchers.is("PRODUCT")));
    }

    @Test
    void getLimitsInvalidNumberTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/rest/limits/2222222235")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addLimitValidTest() throws Exception{
        LimitAdd limit = new LimitAdd(4L, BigDecimal.valueOf(400),"PRODUCT");
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/rest/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(limit)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addLimitInvalidTypeTest() throws Exception{
        LimitAdd limit = new LimitAdd(4L, BigDecimal.valueOf(400),null);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/rest/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(limit)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addLimitOtherTypeTest() throws Exception{
        LimitAdd limit = new LimitAdd(4L, BigDecimal.valueOf(400),"SPORT");
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/rest/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(limit)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

