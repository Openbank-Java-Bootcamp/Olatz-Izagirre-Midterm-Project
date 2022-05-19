package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.CheckingDTO;
import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.CheckingAccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.CheckingAccountServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class CheckingAccountService implements CheckingAccountServiceInterface {
    @Autowired
    private CheckingAccountRepository checkingAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public CheckingAccount createAccount (CheckingDTO checking){
        accountHolderRepository.findById(checking.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Primary account holder not found"));
        Money balance = checking.getBalance();
        String secretKey = checking.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(checking.getPrimaryOwnerId()).get();
        CheckingAccount checkingAccount = new CheckingAccount(balance,primaryOwner,secretKey);
        if(checking.getSecondaryOwnerId()!= null){
            accountHolderRepository.findById(checking.getSecondaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary account holder not found"));
            AccountHolder secondaryOwner = accountHolderRepository.findById(checking.getSecondaryOwnerId()).get();
            checkingAccount.setSecondaryOwner(secondaryOwner);
        }
        checkingAccount.setType(Type.CHECKING);
        //checkingAccount.setSecretKey(passwordEncoder.encode(checkingAccount.getSecretKey()));
        if(checkingAccount.getBalance().getAmount().compareTo(checkingAccount.getMinimumBalance().getAmount())<0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Balance can't be less than minimum balance.");
        }
        return checkingAccountRepository.save(checkingAccount);
    }
}
