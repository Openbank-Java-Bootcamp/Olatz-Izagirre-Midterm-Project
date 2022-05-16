package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.utils.Money;

public class AccountBalanceOnlyDTO {
    private Money balance;

    public AccountBalanceOnlyDTO() {
    }

    public AccountBalanceOnlyDTO(Money balance) {
        this.balance = balance;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
