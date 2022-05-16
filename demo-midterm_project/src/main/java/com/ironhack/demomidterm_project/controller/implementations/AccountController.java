package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.AccountBalanceOnlyDTO;
import com.ironhack.demomidterm_project.controller.interfaces.AccountControllerInterface;
import com.ironhack.demomidterm_project.model.*;
import com.ironhack.demomidterm_project.service.interfaces.*;
import com.ironhack.demomidterm_project.utils.Money;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class AccountController implements AccountControllerInterface {
    @Autowired
    private AccountServiceInterface accountServiceInterface;
    @Autowired
    private CheckingAccountServiceInterface checkingAccountServiceInterface;
    @Autowired
    private StudentCheckingServiceInterface studentCheckingServiceInterface;

    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount (@RequestBody @Valid Account account){
        if (account instanceof CheckingAccount && TimeUnit.DAYS.convert(Date.from(Instant.now()).getTime()-account.getPrimaryOwner().getDateOfBirth().getTime(),TimeUnit.MILLISECONDS) < 8760){
            return studentCheckingServiceInterface.createAccount((StudentChecking)account);
        }
        else {
            return checkingAccountServiceInterface.createAccount((CheckingAccount)account);
        }
    }

    @DeleteMapping ("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount (@PathVariable Long id){
        accountServiceInterface.deleteAccount(id);
    }

    /*@GetMapping ("/accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts (){
        return accountServiceInterface.getAccounts();
    }

    @GetMapping ("/accounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount (@PathVariable Long id){
        return accountServiceInterface.getAccount(id);
    }*/

    @GetMapping ("/accounts/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public Money getAccountBalance (@PathVariable Long id){
        return accountServiceInterface.getAccountBalance(id);
    }

    @PatchMapping("/accounts/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccountBalance (@PathVariable Long id, @RequestBody AccountBalanceOnlyDTO accountBalanceOnlyDTO){
        accountServiceInterface.updateAccountBalance(id,accountBalanceOnlyDTO.getBalance().getAmount());
    }
}
