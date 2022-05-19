package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.model.CreditCard;

public interface CreditCardControllerInterface {
    CreditCard createAccount (CreditCardDTO creditCard);
}
