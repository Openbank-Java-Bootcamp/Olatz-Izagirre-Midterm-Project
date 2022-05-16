package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.AccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService implements AccountServiceInterface {
    @Autowired
    private AccountRepository accountRepository;
}
