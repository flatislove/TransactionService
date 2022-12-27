package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Builder
@Table(schema = "public", name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "type_operation")
    private String type;
    @Column(name = "data_operation")
    private Date date;
    @Column(name = "limit_exceeded")
    private boolean statusFlag;
    @Column(name = "limit_id")
    private long limitId;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private BigDecimal sumOfMoney;
}