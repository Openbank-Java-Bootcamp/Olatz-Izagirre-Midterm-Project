package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.CheckingAccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
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

    public CheckingAccount createAccount (StudentChecking studentChecking){
        CheckingAccount checkingAccount = new CheckingAccount();
            checkingAccount.setBalance(studentChecking.getBalance());
            checkingAccount.setPrimaryOwner(studentChecking.getPrimaryOwner());
            checkingAccount.setSecretKey(studentChecking.getSecretKey());
        if (studentChecking.getSecondaryOwner() != null){
            checkingAccount.setSecondaryOwner(studentChecking.getSecondaryOwner());
        }
        checkingAccount.setSecretKey(passwordEncoder.encode(checkingAccount.getSecretKey()));
        return checkingAccountRepository.save(checkingAccount);
    }
}
