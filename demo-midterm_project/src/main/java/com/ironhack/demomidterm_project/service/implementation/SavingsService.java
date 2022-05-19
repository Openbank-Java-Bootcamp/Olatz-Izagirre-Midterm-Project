package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.SavingsDTO;
import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.CreditCard;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.SavingsRepository;
import com.ironhack.demomidterm_project.service.interfaces.SavingsServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SavingsService implements SavingsServiceInterface {
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /*public Savings createAccount (Savings savings){
        if (savings.getId() != null){
            Optional<Savings> optionalCreditCard = savingsRepository.findById(savings.getId());
            if(optionalCreditCard.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        savings.setSecretKey(passwordEncoder.encode(savings.getSecretKey()));
        savings.setType(Type.SAVINGS);
        return savingsRepository.save(savings);
    }*/

    public Savings createAccount (SavingsDTO savings){
        accountHolderRepository.findById(savings.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Primary account holder not found"));
        Money balance = savings.getBalance();
        String secretKey = savings.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(savings.getPrimaryOwnerId()).get();
        Savings savingsAccount = new Savings(balance,primaryOwner,secretKey);
        if(savings.getSecondaryOwnerId()!= null){
            accountHolderRepository.findById(savings.getSecondaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary account holder not found"));
            AccountHolder secondaryOwner = accountHolderRepository.findById(savings.getSecondaryOwnerId()).get();
            savingsAccount.setSecondaryOwner(secondaryOwner);
        }
        if(savings.getMinimumBalance()!= null){
            Money minimumBalance = savings.getMinimumBalance();
            savingsAccount.setMinimumBalance(minimumBalance);
        }
        if(savings.getInterestRate()!= null){
            BigDecimal interestRate = savings.getInterestRate();
            savingsAccount.setInterestRate(interestRate);
        }
        //savingsAccount.setSecretKey(passwordEncoder.encode(savings.getSecretKey()));
        savingsAccount.setType(Type.SAVINGS);
        if(savingsAccount.getBalance().getAmount().compareTo(savingsAccount.getMinimumBalance().getAmount())<0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Balance can't be less than minimum balance.");
        }
        return savingsRepository.save(savingsAccount);
    }

    public void savingsInterests (){
        List<Savings> savings = savingsRepository.findAll();
        for(Savings saving : savings){
            if (saving.getLastInterestDate()==null){
                if(TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-saving.getCreationDate().getTime(),TimeUnit.MILLISECONDS)> 365){
                    BigDecimal balance = saving.getBalance().getAmount();
                    BigDecimal rate = saving.getInterestRate();
                    saving.setBalance(new Money(balance.add(balance.multiply(rate))));
                    saving.setLastInterestDate(Date.from(Instant.now()));
                    savingsRepository.save(saving);
                }
            }else{
                if(TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-saving.getLastInterestDate().getTime(),TimeUnit.MILLISECONDS)> 365){
                    BigDecimal balance = saving.getBalance().getAmount();
                    BigDecimal rate = saving.getInterestRate();
                    saving.setBalance(new Money(balance.add(balance.multiply(rate))));
                    saving.setLastInterestDate(Date.from(Instant.now()));
                    savingsRepository.save(saving);
            }
        }
    }
}
}
