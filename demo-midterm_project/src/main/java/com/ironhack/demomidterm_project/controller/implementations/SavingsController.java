package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.SavingsControllerInterface;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.service.interfaces.SavingsServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SavingsController implements SavingsControllerInterface {
    @Autowired
    private SavingsServiceInterface savingsServiceInterface;

    @PostMapping("/accounts/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings createAccount (@RequestBody @Valid Savings savings){
        return savingsServiceInterface.createAccount(savings);
    }
}
