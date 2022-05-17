package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.controller.interfaces.AccountHolderControllerInterface;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.service.interfaces.AccountHolderServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AccountHolderController implements AccountHolderControllerInterface {
    @Autowired
    private AccountHolderServiceInterface accountHolderServiceInterface;
    @PostMapping("/users/accountHolders")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder (@RequestBody @Valid AccountHolder accountHolder){
        return accountHolderServiceInterface.createAccountHolder(accountHolder);
    }


}
