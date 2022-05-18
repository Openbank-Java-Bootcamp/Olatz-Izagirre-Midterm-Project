package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.CreditCard;
import com.ironhack.demomidterm_project.repository.CreditCardRepository;
import com.ironhack.demomidterm_project.service.interfaces.CreditCardServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
        creditCard.setType(Type.CREDIT_CARD);
        return creditCardRepository.save(creditCard);
    }

    public void creditCardInterests(){
        List<CreditCard> creditCards = creditCardRepository.findAll();
        for(CreditCard creditCard : creditCards){
            if (creditCard.getLastInterestDate()==null){
                if(TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-creditCard.getCreationDate().getTime(),TimeUnit.MILLISECONDS)> 30){
                    BigDecimal balance = creditCard.getBalance().getAmount();
                    BigDecimal rate = creditCard.getInterestRate();
                    creditCard.setBalance(new Money(balance.add(balance.multiply(rate))));
                    creditCard.setLastInterestDate(Date.from(Instant.now()));
                    creditCardRepository.save(creditCard);
                }
            }else{
                if(TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-creditCard.getLastInterestDate().getTime(),TimeUnit.MILLISECONDS)> 30){
                    BigDecimal balance = creditCard.getBalance().getAmount();
                    BigDecimal rate = creditCard.getInterestRate();
                    creditCard.setBalance(new Money(balance.add(balance.multiply(rate))));
                    creditCard.setLastInterestDate(Date.from(Instant.now()));
                    creditCardRepository.save(creditCard);
                }
            }
        }

    }
}
