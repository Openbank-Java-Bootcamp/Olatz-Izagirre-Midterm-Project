package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.DTO.SavingsDTO;
import com.ironhack.demomidterm_project.model.Savings;

public interface SavingsServiceInterface {
    Savings createAccount (SavingsDTO savings);
}
