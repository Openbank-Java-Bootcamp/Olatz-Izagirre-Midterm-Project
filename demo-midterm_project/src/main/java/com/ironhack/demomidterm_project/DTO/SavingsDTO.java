package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.utils.Money;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SavingsDTO {
    @NotNull
    private Money balance;
    @NotNull
    private Long primaryOwnerId;
    private Long secondaryOwnerId;
    @Valid
    private Money minimumBalance;
    @DecimalMax("0.5")
    private BigDecimal interestRate;
    @NotNull
    private String secretKey;

    public SavingsDTO() {
    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, Money minimumBalance, BigDecimal interestRate, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.secretKey = secretKey;

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Money minimumBalance, BigDecimal interestRate, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.secretKey = secretKey;

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, Money minimumBalance, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Money minimumBalance, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.interestRate = BigDecimal.valueOf(0.0025);

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, Long secondaryOwnerId, BigDecimal interestRate, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.secondaryOwnerId = secondaryOwnerId;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));

    }

    public SavingsDTO(Money balance, Long primaryOwnerId, BigDecimal interestRate, String secretKey) {
        this.balance = balance;
        this.primaryOwnerId = primaryOwnerId;
        this.interestRate = interestRate;
        this.secretKey = secretKey;
        this.minimumBalance = new Money(BigDecimal.valueOf(1000L));

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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
