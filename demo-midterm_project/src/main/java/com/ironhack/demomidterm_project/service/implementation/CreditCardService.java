package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.CreditCard;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.repository.CreditCardRepository;
import com.ironhack.demomidterm_project.service.interfaces.CreditCardServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class CreditCardService implements CreditCardServiceInterface {
    @Autowired
    private CreditCardRepository creditCardRepository;

    public CreditCard createAccount(CreditCard creditCard){
        if (creditCard.getId() != null){
            Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCard.getId());
            if(optionalCreditCard.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        return creditCardRepository.save(creditCard);
    }
}
