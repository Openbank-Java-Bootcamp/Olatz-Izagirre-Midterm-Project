package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.Savings;
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

    public Savings createAccount (Savings savings){
        if (savings.getId() != null){
            Optional<Savings> optionalSavings = savingsRepository.findById(savings.getId());
            if(optionalSavings.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        savings.setSecretKey(passwordEncoder.encode(savings.getSecretKey()));
        savings.setType(Type.SAVINGS);
        return savingsRepository.save(savings);
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
