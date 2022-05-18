package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.TransferDTO;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.AccountRepository;
import com.ironhack.demomidterm_project.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

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

    public List<Account> getUsersAccounts(String username){
       Long id = userRepository.findByUsername(username).getId();
       return accountRepository.findByAccountOwnerId(id);
    }

    public void moneyTransfer (Long id, TransferDTO transferDTO){
        Account transferMaker = accountRepository.findById(id).get();
        BigDecimal transferAmount = transferDTO.getAmount().getAmount();
        if(transferMaker.getBalance().getAmount().compareTo(transferAmount)>=0){
            Long receiverId = transferDTO.getAccountId();
            Account transferReceiver = accountRepository.findById(receiverId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
            Long ownerId = transferDTO.getOwnerId();
            if(transferReceiver.getPrimaryOwner().equals(accountHolderRepository.findById(ownerId).get())){
                BigDecimal newBalanceMaker = transferMaker.getBalance().getAmount().subtract(transferAmount);
                BigDecimal newBalanceReceiver = transferReceiver.getBalance().getAmount().add(transferAmount);
                transferMaker.setBalance(new Money(newBalanceMaker));
                transferReceiver.setBalance(new Money(newBalanceReceiver));
                accountRepository.save(transferMaker);
                accountRepository.save(transferReceiver);
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong account.");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough funds.");
        }
    }

}
