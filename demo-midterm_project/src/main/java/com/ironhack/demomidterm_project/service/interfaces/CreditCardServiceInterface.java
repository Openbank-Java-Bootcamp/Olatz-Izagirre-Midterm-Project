package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.model.CreditCard;

public interface CreditCardServiceInterface {
    CreditCard createAccount (CreditCardDTO creditCard);
}
