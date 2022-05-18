package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.enums.Status;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CheckingAccount extends Account{
    @NotNull
    private String secretKey;
    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "minimum_balance_amount")),

    })
    @Column(length = 510)
    @Embedded
    private final Money minimumBalance;
    @AttributeOverrides({
            @AttributeOverride( name = "currency" , column = @Column(name = "maintenance_fee_currency")),
            @AttributeOverride( name = "amount" , column = @Column(name = "maintenance_fee_amount")),

    })
    @Column(length = 510)
    @Embedded
    private final Money maintenanceFee;
    @Enumerated (EnumType.STRING)
    private Status status;

    public CheckingAccount() {
        this.maintenanceFee = new Money(BigDecimal.valueOf(12L));
        this.minimumBalance = new Money(BigDecimal.valueOf(250L));
        this.status = Status.ACTIVE;
        setType(Type.CHECKING);
    }

    public CheckingAccount(Money balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.maintenanceFee = new Money(BigDecimal.valueOf(12L));
        this.minimumBalance = new Money(BigDecimal.valueOf(250L));
        this.status = Status.ACTIVE;
        setType(Type.CHECKING);
    }

    public CheckingAccount(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.maintenanceFee = new Money(BigDecimal.valueOf(12L));
        this.minimumBalance = new Money(BigDecimal.valueOf(250L));
        this.status = Status.ACTIVE;
        setType(Type.CHECKING);
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

    public Money getMaintenanceFee() {
        return maintenanceFee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
