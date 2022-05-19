package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.CheckingDTO;
import com.ironhack.demomidterm_project.model.Account;

public interface StudentCheckingControllerInterface {
    Account createAccount (CheckingDTO checkingDto);
}
