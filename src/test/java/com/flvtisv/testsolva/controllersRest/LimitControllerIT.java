package com.flvtisv.testsolva.controllersRest;

import com.flvtisv.testsolva.service.LimitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class LimitControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    LimitService limitService;

    @Test
    void addLimitInvalidTypeOtherLimitTest() throws Exception{
//        var requestBuilder = MockMvcRequestBuilders.post("/rest/limits")

    }
}

