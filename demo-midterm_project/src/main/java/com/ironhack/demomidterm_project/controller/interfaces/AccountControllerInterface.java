package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.AccountBalanceOnlyDTO;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.utils.Money;

import java.util.List;

public interface AccountControllerInterface {
    Account createAccount(Account account);
    void deleteAccount (Long id);

    /*Account getAccount (Long id);

    List<Account> getAccounts ();*/

    Money getAccountBalance (Long id);

    void updateAccountBalance (Long id, AccountBalanceOnlyDTO accountBalanceOnlyDTO);
}
