package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.AccountBalanceOnlyDTO;
import com.ironhack.demomidterm_project.DTO.AccountDTO;
import com.ironhack.demomidterm_project.DTO.ThirdPartyTransferDTO;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.utils.Money;

import java.security.Principal;
import java.util.List;

public interface AccountControllerInterface {
    void deleteAccount (Long id);

    /*Account getAccount (Long id);

    List<Account> getAccounts ();*/

    Money getAccountBalance (Long id);

    void updateAccountBalance (Long id, AccountBalanceOnlyDTO accountBalanceOnlyDTO);

   List<AccountDTO> getUsersAccounts (String username, Principal principal);

   void sendMoney (ThirdPartyTransferDTO thirdPartyTransferDTO);

   void receiveMoney (ThirdPartyTransferDTO thirdPartyTransferDTO);
}
