package com.flvtisv.testsolva.service.impl;

import com.flvtisv.testsolva.repository.LimitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

@ExtendWith(MockitoExtension.class)
public class LimitServiceImplTest {

    @Mock
    private LimitRepository limitRepository;

    @InjectMocks
    private LimitServiceImpl limitService;

    @Test
    public void getFormatDateTest(){
        String date = limitService.getFormatDate();
        boolean isFormat = Pattern.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}[+|-]\\d{2}$",date);
        Assertions.assertTrue(isFormat);
    }
}
