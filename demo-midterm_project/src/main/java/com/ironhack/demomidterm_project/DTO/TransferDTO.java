package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.utils.Money;

public class TransferDTO {
    private Money amount;
    private Long ownerId;
    private Long accountId;

    public TransferDTO() {
    }

    public TransferDTO(Money amount, Long ownerId, Long accountId) {
        this.amount = amount;
        this.ownerId = ownerId;
        this.accountId = accountId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
