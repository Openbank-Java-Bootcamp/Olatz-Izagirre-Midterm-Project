package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.repository.SavingsRepository;
import com.ironhack.demomidterm_project.service.interfaces.SavingsServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
}
