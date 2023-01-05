package com.flvtisv.testsolva.controllersGraphql;

import com.flvtisv.testsolva.controllersGraphql.dto.TransactionView;
import com.flvtisv.testsolva.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    TransactionService transactionService;

    @InjectMocks
    TransactionController transactionController;

    @Test
    void transactionsTest(){
        var transactions = List.of(
                new TransactionView(1L,"2222222222","SERVICE","2023-01-05 00:16:47+06",false,1L,"USD",BigDecimal.valueOf(2000)),
                new TransactionView(2L,"2222222222","PRODUCT","2023-01-06 00:18:47+06",false,2L,"KZT",BigDecimal.valueOf(2000)));

        Mockito.doReturn(transactions).when(this.transactionService).getListTransactionsViewFromRepository();
        var response = this.transactionController.transactions();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(transactions, response);
    }

    @Test
    void transactionByIdTest(){



    }
}
