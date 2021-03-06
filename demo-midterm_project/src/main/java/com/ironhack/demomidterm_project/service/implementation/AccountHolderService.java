package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.RoleRepository;
import com.ironhack.demomidterm_project.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    public AccountHolder createAccountHolder (AccountHolder accountHolder){
        if(userRepository.findByUsername(accountHolder.getUsername())!= null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Username already exists");
        }
        if (accountHolder.getId() != null){
            Optional<AccountHolder> optionalAccountHolder = accountHolderRepository.findById(accountHolder.getId());
            if(optionalAccountHolder.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account Holder already exists");
        }
        accountHolder.setRole(roleRepository.findByName("ACCOUNT_HOLDER"));
        accountHolder.setPassword(passwordEncoder.encode("123456"));
        return accountHolderRepository.save(accountHolder);
    }

}
