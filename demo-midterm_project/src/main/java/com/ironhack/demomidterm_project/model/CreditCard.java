package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account{
    @Max(100000L)
    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "credit_limit_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "credit_limit_amount")),

    })
    @Column(length = 510)
    @Embedded
    private Money creditLimit;
    @DecimalMin("0.1")
    private BigDecimal interestRate;
    private Date lastInterestDate;

    public CreditCard(Money balance, AccountHolder primaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, Money creditLimit) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Money creditLimit) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = BigDecimal.valueOf(0.2);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = BigDecimal.valueOf(0.2);
        this.creditLimit = new Money(BigDecimal.valueOf(100L));
    }
}
