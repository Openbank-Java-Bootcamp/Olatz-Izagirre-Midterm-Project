package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.DTO.CheckingDTO;
import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;

public interface StudentCheckingServiceInterface {
    StudentChecking createAccount (CheckingDTO studentChecking);
}
