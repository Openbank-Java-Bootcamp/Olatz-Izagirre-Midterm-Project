package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.RoleRepository;
import com.ironhack.demomidterm_project.service.interfaces.AccountHolderServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
@Slf4j
public class AccountHolderService implements AccountHolderServiceInterface {
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public AccountHolder createAccountHolder (AccountHolder accountHolder){
        if (accountHolder.getId() != null){
            Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(accountHolder.getId());
            if(optionalAccountHolder.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account Holder already exists");
        }
        accountHolder.setRole(roleRepository.findByName("ACCOUNT_HOLDER"));
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        return accountHolderRepository.save(accountHolder);
    }

    public void changePassword (Long id, UserPasswordOnlyDTO userPasswordOnlyDTO){
        AccountHolder accountHolderFromDb = accountHolderRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account Holder not found"));
        accountHolderFromDb.setPassword(passwordEncoder.encode(userPasswordOnlyDTO.getPassword()));
        accountHolderRepository.save(accountHolderFromDb);
    }
}
