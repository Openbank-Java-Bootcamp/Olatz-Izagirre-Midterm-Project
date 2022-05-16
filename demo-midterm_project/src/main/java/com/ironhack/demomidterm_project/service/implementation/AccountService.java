package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.service.interfaces.AccountServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class AccountService implements AccountServiceInterface {
    @Autowired
    private AccountRepository accountRepository;

    public void deleteAccount (Long id){
        accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        accountRepository.deleteById(id);
    }

    public Account getAccount (Long id){
        return accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
    }

    public List<Account> getAccounts (){
        return accountRepository.findAll();
    }

    public Money getAccountBalance (Long id){
        accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        return accountRepository.findById(id).get().getBalance();
    }

    public void updateAccountBalance (Long id, BigDecimal amount){
        Account accountFromDb = accountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        accountFromDb.setBalance(new Money(amount));
        accountRepository.save(accountFromDb);
    }
}
