package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "balance_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "balance_amount")),

    })
    @Column(length = 510)
    @Embedded
    private Money balance;
    @NotNull
    @ManyToOne
    @JoinColumn (name = "primary_account_holder_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @JoinColumn (name = "secondary_account_holder_id")
    private AccountHolder secondaryOwner;

    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "penalty_fee_amount")),

    })
    @Column(length = 510)
    @Embedded
    private final Money penaltyFee;
    @PastOrPresent
    private final Date creationDate;

    public Account() {
        this.creationDate = Date.from(Instant.now());
        this.penaltyFee = new Money(BigDecimal.valueOf(40L));
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationDate = Date.from(Instant.now());
        this.penaltyFee = new Money(BigDecimal.valueOf(40L));
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = Date.from(Instant.now());
        this.penaltyFee = new Money(BigDecimal.valueOf(40L));
    }
}
