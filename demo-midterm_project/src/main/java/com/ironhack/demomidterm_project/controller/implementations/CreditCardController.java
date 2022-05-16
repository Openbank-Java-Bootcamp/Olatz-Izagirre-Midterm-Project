package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.CreditCardControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.CreditCardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CreditCardController implements CreditCardControllerInterface {
    @Autowired
    private CreditCardServiceInterface creditCardServiceInterface;
}
