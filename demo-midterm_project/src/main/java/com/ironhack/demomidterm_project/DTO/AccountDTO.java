package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.utils.Money;

public class AccountDTO {
    private Long accountId;
    private Money balance;
    private Type type;

    public AccountDTO(Long accountId, Money balance, Type type) {
        this.accountId = accountId;
        this.balance = balance;
        this.type = type;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
