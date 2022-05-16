package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.SavingsControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.SavingsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SavingsController implements SavingsControllerInterface {
    @Autowired
    private SavingsServiceInterface savingsServiceInterface;
}
