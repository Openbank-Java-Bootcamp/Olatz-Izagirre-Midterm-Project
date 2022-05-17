package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.model.AccountHolder;

public interface AccountHolderControllerInterface {
    /*void passwordChange (Long id, UserPasswordOnlyDTO userPasswordOnlyDTO);*/

    AccountHolder createAccountHolder (AccountHolder accountHolder);
}
