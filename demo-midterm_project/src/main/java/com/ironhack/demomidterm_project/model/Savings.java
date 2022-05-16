package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Status;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account{
    @NotNull
    @Digits(integer = 4,fraction = 0)
    private Long secretKey;
    @Min(100L)
    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "minimum_balance_amount")),

    })
    @Column(length = 510)
    @Embedded
    private Money minimumBalance;
    @DecimalMax("0.5")
    private BigDecimal interestRate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Date lastInterestDate;

    public Savings(Money balance, AccountHolder primaryOwner, Long secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Long secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Long secretKey, Money minimumBalance) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
    }

    public Savings(Money balance, AccountHolder primaryOwner, Long secretKey, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
    }

    public Savings(Money balance, AccountHolder primaryOwner, Long secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Long secretKey, Money minimumBalance) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Long secretKey, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.interestRate = interestRate;
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Long secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.status = Status.ACTIVE;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));
    }

    public Long getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Long secretKey) {
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
