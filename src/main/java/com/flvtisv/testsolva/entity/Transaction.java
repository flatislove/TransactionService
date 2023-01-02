package com.flvtisv.testsolva.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "public", name = "transactions")
@Schema(description = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "transaction ID")
    private int Id;
    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(description = "transaction account")
    private Account account;
    @Column(name = "account_to")
    @Schema(description = "beneficiary's account number")
    private String accountTo;
    @Column(name = "type_operation")
    @Schema(description = "expense type")
    private String type;
    @Column(name = "data_operation")
    @Schema(description = "transaction data")
    private String date;
    @Column(name = "limit_exceeded")
    @Schema(description = "exceeded limit")
    private boolean statusFlag;
    @Column(name = "limit_id")
    @Schema(description = "limit ID")
    private long limitId;
    @Column(name = "currency")
    @Schema(description = "Currency")
    private String currency;
    @Column(name = "amount")
    @Schema(description = "transaction sum")
    private BigDecimal sumOfMoney;

    public Transaction(Account account, String accountTo, String type, String date, boolean statusFlag,
                       long limitId, String currency, BigDecimal sumOfMoney) {
        this.account = account;
        this.accountTo = accountTo;
        this.type = type;
        this.date = date;
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

}