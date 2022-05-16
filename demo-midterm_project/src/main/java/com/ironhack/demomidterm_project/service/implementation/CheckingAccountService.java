package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.repository.CheckingAccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {
    @Autowired
    private CheckingAccountRepository checkingAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CheckingAccount createAccount (CheckingAccount checkingAccount){
        if (checkingAccount.getId() != null){
            Optional<CheckingAccount> optionalCheckingAccount = checkingAccountRepository.findById(checkingAccount.getId());
            if(optionalCheckingAccount.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        checkingAccount.setSecretKey(passwordEncoder.encode(checkingAccount.getSecretKey()));
        return checkingAccountRepository.save(checkingAccount);
    }
}
