package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.CheckingAccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        checkingAccount.setType(Type.CHECKING);
        checkingAccount.setSecretKey(passwordEncoder.encode(checkingAccount.getSecretKey()));
        return checkingAccountRepository.save(checkingAccount);
    }
}
