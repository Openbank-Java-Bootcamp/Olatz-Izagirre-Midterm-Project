package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.repository.CheckingAccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {
    @Autowired
    private CheckingAccountRepository checkingAccountRepository;
}
