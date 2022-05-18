package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.enums.Status;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account{
    @NotNull
    private String secretKey;
    //@Min(100L)
    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "minimum_balance_amount")),

    })
    @Column(length = 510)
    @Embedded
    @Valid
    private Money minimumBalance;
    @DecimalMax("0.5")
    @Column(precision = 32,scale = 4)
    private BigDecimal interestRate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date lastInterestDate;

    public Savings() {
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, String secretKey, Money minimumBalance) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, String secretKey, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Money minimumBalance) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
        setType(Type.SAVINGS);
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
        setType(Type.SAVINGS);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(Date lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
