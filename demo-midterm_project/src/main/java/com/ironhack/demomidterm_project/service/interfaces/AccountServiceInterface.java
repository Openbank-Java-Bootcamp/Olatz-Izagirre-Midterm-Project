package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.DTO.TransferDTO;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.utils.Money;

import java.math.BigDecimal;
import java.util.List;

public interface AccountServiceInterface {
    void deleteAccount (Long id);
    Account getAccount (Long id);
    List<Account> getAccounts ();
    Money getAccountBalance (Long id);

    void updateAccountBalance(Long id, BigDecimal amount);

    List<Account> getUsersAccounts (String username);
    void moneyTransfer (Long id, TransferDTO transferDTO);

}
