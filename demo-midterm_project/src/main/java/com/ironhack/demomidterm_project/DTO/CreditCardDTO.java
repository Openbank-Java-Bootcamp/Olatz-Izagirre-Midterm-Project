package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.utils.Money;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreditCardDTO {
    @NotNull
    private Money balance;
    @NotNull
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @Valid
    private Money creditLimit;
    @DecimalMin("0.1")
    private BigDecimal interestRate;

    public CreditCardDTO() {
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, Money creditLimit, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Money creditLimit, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, Money creditLimit) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Money creditLimit) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCardDTO(Money balance, Long primaryOwnerId, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Long getPrimaryOwnerId() {
        return primaryOwnerId;
    }

    public void setPrimaryOwnerId(Long primaryOwnerId) {
        this.primaryOwnerId = primaryOwnerId;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
