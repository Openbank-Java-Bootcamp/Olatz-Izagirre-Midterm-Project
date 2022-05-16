package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.enums.Status;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{
    @NotNull
    @Digits(integer = 4,fraction = 0)
    private Long secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentChecking(Money balance, AccountHolder primaryOwner, Long secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, Long secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public Long getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Long secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
