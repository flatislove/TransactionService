package com.flvtisv.testsolva.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private Account account;
    @Column(name = "account_to")
    private String accountTo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}