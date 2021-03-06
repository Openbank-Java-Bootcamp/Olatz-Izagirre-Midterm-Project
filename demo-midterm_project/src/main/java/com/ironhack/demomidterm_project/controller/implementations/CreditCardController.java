package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.controller.interfaces.CreditCardControllerInterface;
import com.ironhack.demomidterm_project.model.CreditCard;
import com.ironhack.demomidterm_project.service.interfaces.CreditCardServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CreditCardController implements CreditCardControllerInterface {
    @Autowired
    private CreditCardServiceInterface creditCardServiceInterface;
    @PostMapping ("/accounts/creditCards")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard createAccount (@RequestBody @Valid CreditCardDTO creditCard){
        return creditCardServiceInterface.createAccount(creditCard);
    }
}
