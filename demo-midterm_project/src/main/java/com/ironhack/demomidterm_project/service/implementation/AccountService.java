package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.ThirdPartyTransferDTO;
import com.ironhack.demomidterm_project.DTO.TransferDTO;
import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.Account;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.repository.*;
import com.ironhack.demomidterm_project.service.interfaces.AccountServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AccountService implements AccountServiceInterface {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

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
        if(accountFromDb.getType()== Type.SAVINGS){
            BigDecimal minimumBalance = savingsRepository.findById(id).get().getMinimumBalance().getAmount();
            if (amount.compareTo(minimumBalance)<0){
                amount = amount.subtract(savingsRepository.findById(id).get().getPenaltyFee().getAmount());
            }
        }
        else if (accountFromDb.getType()== Type.CHECKING){
            BigDecimal minimumBalance = checkingAccountRepository.findById(id).get().getMinimumBalance().getAmount();
            if (amount.compareTo(minimumBalance)<0){
                amount = amount.subtract(checkingAccountRepository.findById(id).get().getPenaltyFee().getAmount());
            }}
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
            AccountHolder owner = accountHolderRepository.findById(ownerId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Owner not found"));
            if(transferReceiver.getPrimaryOwner().equals(owner)|| transferReceiver.getSecondaryOwner().equals(owner)){
                BigDecimal newBalanceMaker = transferMaker.getBalance().getAmount().subtract(transferAmount);
                BigDecimal newBalanceReceiver = transferReceiver.getBalance().getAmount().add(transferAmount);
                if(transferMaker.getType()== Type.SAVINGS){
                    BigDecimal minimumBalance = savingsRepository.findById(id).get().getMinimumBalance().getAmount();
                    if (newBalanceMaker.compareTo(minimumBalance)<0){
                        newBalanceMaker = newBalanceMaker.subtract(savingsRepository.findById(id).get().getPenaltyFee().getAmount());
                    }
                }
                else if (transferMaker.getType()== Type.CHECKING){
                    BigDecimal minimumBalance = checkingAccountRepository.findById(id).get().getMinimumBalance().getAmount();
                    if (newBalanceMaker.compareTo(minimumBalance)<0){
                        newBalanceMaker = newBalanceMaker.subtract(checkingAccountRepository.findById(id).get().getPenaltyFee().getAmount());
                    }
                }
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

    public void sendMoney(ThirdPartyTransferDTO thirdPartyTransferDTO){
        Long accountId = thirdPartyTransferDTO.getAccountId();
        accountRepository.findById(accountId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        Account receiver = accountRepository.findById(accountId).get();
        if(receiver.getType()==Type.CREDIT_CARD){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You can't send money to this account.");
        }
        if (receiver.getType()==Type.CHECKING){
            String secretKey = checkingAccountRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }
        else if (receiver.getType()==Type.STUDENT_CHECKING){
            String secretKey = studentCheckingRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }
        else if (receiver.getType()==Type.SAVINGS){
            String secretKey = savingsRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }
        BigDecimal newBalance = receiver.getBalance().getAmount().add(thirdPartyTransferDTO.getAmount().getAmount());
        receiver.setBalance(new Money(newBalance));
        accountRepository.save(receiver);
    }

    public void receiveMoney(ThirdPartyTransferDTO thirdPartyTransferDTO){
        Long accountId = thirdPartyTransferDTO.getAccountId();
        accountRepository.findById(accountId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found"));
        Account sender = accountRepository.findById(accountId).get();
        if(sender.getType()==Type.CREDIT_CARD){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You can't send money to this account.");
        }
        if (sender.getType()==Type.CHECKING){
            String secretKey = checkingAccountRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }
        else if (sender.getType()==Type.STUDENT_CHECKING){
            String secretKey = studentCheckingRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }
        else if (sender.getType()==Type.SAVINGS){
            String secretKey = savingsRepository.findById(accountId).get().getSecretKey();
            if (!Objects.equals(secretKey, thirdPartyTransferDTO.getSecretKey())){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Wrong secret key.");
            }
        }if(sender.getBalance().getAmount().compareTo(thirdPartyTransferDTO.getAmount().getAmount())>=0){
            BigDecimal newBalance = sender.getBalance().getAmount().subtract(thirdPartyTransferDTO.getAmount().getAmount());
            if(sender.getType()== Type.SAVINGS){
                BigDecimal minimumBalance = savingsRepository.findById(accountId).get().getMinimumBalance().getAmount();
                if (newBalance.compareTo(minimumBalance)<0){
                    newBalance = newBalance.subtract(savingsRepository.findById(accountId).get().getPenaltyFee().getAmount());
                }
            }
            else if (sender.getType()== Type.CHECKING){
                BigDecimal minimumBalance = checkingAccountRepository.findById(accountId).get().getMinimumBalance().getAmount();
                if (newBalance.compareTo(minimumBalance)<0){
                    newBalance = newBalance.subtract(checkingAccountRepository.findById(accountId).get().getPenaltyFee().getAmount());
                }
            }
            sender.setBalance(new Money(newBalance));
            accountRepository.save(sender);
    }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough funds.");
        }
    }
}
