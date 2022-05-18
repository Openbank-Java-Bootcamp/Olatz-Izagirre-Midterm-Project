package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account{

    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "credit_limit_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "credit_limit_amount")),

    })
    @Column(length = 510)
    @Embedded
    @Valid
    private Money creditLimit;
    @DecimalMin("0.1")
    private BigDecimal interestRate;
    private Date lastInterestDate;

    public CreditCard() {
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Money creditLimit) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
        setType(Type.CREDIT_CARD);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
        setType(Type.CREDIT_CARD);
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

    public Date getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(Date lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
