package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.CreditCardDTO;
import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.CreditCard;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
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
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /*public CreditCard createAccount(CreditCard creditCard){
        if (creditCard.getId() != null){
            Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCard.getId());
            if(optionalCreditCard.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        creditCard.setType(Type.CREDIT_CARD);
        return creditCardRepository.save(creditCard);
    }*/

    public CreditCard createAccount(CreditCardDTO creditCard){
        accountHolderRepository.findById(creditCard.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Primary account holder not found"));
        Money balance = creditCard.getBalance();
        AccountHolder primaryOwner = accountHolderRepository.findById(creditCard.getPrimaryOwnerId()).get();
        CreditCard creditCardAccount = new CreditCard(balance,primaryOwner);
        if(creditCard.getSecondaryOwnerId()!= null){
            accountHolderRepository.findById(creditCard.getSecondaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary account holder not found"));
            AccountHolder secondaryOwner = accountHolderRepository.findById(creditCard.getSecondaryOwnerId()).get();
            creditCardAccount.setSecondaryOwner(secondaryOwner);
        }
        if(creditCard.getCreditLimit()!= null){
            Money creditLimit = creditCard.getCreditLimit();
            creditCardAccount.setCreditLimit(creditLimit);
        }
        if(creditCard.getInterestRate()!= null){
            BigDecimal interestRate = creditCard.getInterestRate();
            creditCardAccount.setInterestRate(interestRate);
        }
        creditCardAccount.setType(Type.CREDIT_CARD);
        return creditCardRepository.save(creditCardAccount);
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
