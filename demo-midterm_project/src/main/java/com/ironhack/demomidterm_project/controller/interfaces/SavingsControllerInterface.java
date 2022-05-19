package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.SavingsDTO;
import com.ironhack.demomidterm_project.model.Savings;

public interface SavingsControllerInterface {
    Savings createAccount (SavingsDTO savings);
}
