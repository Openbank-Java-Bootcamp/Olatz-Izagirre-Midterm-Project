package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.CheckingAccountControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckingAccountController implements CheckingAccountControllerInterface {
    @Autowired
    private CheckingAccountServiceInterface checkingAccountServiceInterface;
}
