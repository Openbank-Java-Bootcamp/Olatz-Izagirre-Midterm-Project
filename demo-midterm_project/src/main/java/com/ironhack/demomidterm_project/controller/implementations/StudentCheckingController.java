package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.CheckingDTO;
import com.ironhack.demomidterm_project.controller.interfaces.StudentCheckingControllerInterface;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import com.ironhack.demomidterm_project.service.interfaces.StudentCheckingServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class StudentCheckingController implements StudentCheckingControllerInterface {
    @Autowired
    private StudentCheckingServiceInterface studentCheckingServiceInterface;
    @Autowired
    private CheckingAccountServiceInterface checkingAccountServiceInterface;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @PostMapping("/accounts/checkingAccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount (@RequestBody @Valid CheckingDTO account){
        AccountHolder primaryAccountHolder = accountHolderRepository.findById(account.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account holder not found"));
        if (TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-primaryAccountHolder.getDateOfBirth().getTime(),TimeUnit.MILLISECONDS) < 8760){
            return studentCheckingServiceInterface.createAccount(account);
        }
        else {
            return checkingAccountServiceInterface.createAccount(account);
        }
    }
}
