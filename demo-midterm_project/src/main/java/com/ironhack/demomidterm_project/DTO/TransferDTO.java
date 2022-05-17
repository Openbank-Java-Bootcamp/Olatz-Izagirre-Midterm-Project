package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.utils.Money;

public class TransferDTO {
    private Money amount;
    private AccountHolder owner;
    private Long accountId;

    public TransferDTO() {
    }

    public TransferDTO(Money amount, AccountHolder owner, Long accountId) {
        this.amount = amount;
        this.owner = owner;
        this.accountId = accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public AccountHolder getOwner() {
        return owner;
    }

    public void setOwner(AccountHolder owner) {
        this.owner = owner;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
