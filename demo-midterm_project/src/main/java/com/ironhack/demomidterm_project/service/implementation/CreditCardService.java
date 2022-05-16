package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.repository.CreditCardRepository;
import com.ironhack.demomidterm_project.service.interfaces.CreditCardServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreditCardService implements CreditCardServiceInterface {
    @Autowired
    private CreditCardRepository creditCardRepository;
}
