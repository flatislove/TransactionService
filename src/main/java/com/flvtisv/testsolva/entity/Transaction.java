package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "public", name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;
    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @Column(name = "account_to")
    private String accountTo;
    @Column(name = "type_operation")
    private String type;
    @Column(name = "data_operation")
    private String date;
    @Column(name = "limit_exceeded")
    private boolean statusFlag;
    @Column(name = "limit_id")
    private long limitId;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private BigDecimal sumOfMoney;

    public Transaction(Account account, String accountTo, String type, boolean statusFlag,
                       long limitId, String currency, BigDecimal sumOfMoney) {
        this.account = account;
        this.accountTo = accountTo;
        this.type = type;
        this.date = getFormatDate();
        this.statusFlag = statusFlag;
        this.limitId = limitId;
        this.currency = currency;
        this.sumOfMoney = sumOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null) {
            Hibernate.getClass(this);
            Hibernate.getClass(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @SneakyThrows
    public String getFormatDate() {
        String pattern = "yyyy-MM-dd' 'HH:mm:ssX";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}