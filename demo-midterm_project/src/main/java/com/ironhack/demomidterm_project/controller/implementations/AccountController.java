package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.AccountControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController implements AccountControllerInterface {
    @Autowired
    private AccountHolderServiceInterface accountHolderServiceInterface;
}
