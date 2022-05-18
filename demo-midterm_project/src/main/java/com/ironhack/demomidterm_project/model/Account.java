package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
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

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn (name = "primary_account_holder_id")
    private AccountHolder primaryOwner;
    @ManyToOne (cascade = CascadeType.MERGE)
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
    @Enumerated(EnumType.STRING)
    private Type type;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
