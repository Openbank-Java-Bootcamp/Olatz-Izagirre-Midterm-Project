package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.DTO.CheckingDTO;
import com.ironhack.demomidterm_project.enums.Type;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Savings;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.AccountHolderRepository;
import com.ironhack.demomidterm_project.repository.StudentCheckingRepository;
import com.ironhack.demomidterm_project.service.interfaces.StudentCheckingServiceInterface;
import com.ironhack.demomidterm_project.utils.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class StudentCheckingService implements StudentCheckingServiceInterface {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /*public StudentChecking createAccount (StudentChecking studentChecking){
        if (studentChecking.getId() != null){
            Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(studentChecking.getId());
            if(optionalStudentChecking.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        studentChecking.setSecretKey(passwordEncoder.encode(studentChecking.getSecretKey()));
        studentChecking.setType(Type.STUDENT_CHECKING);
        return studentCheckingRepository.save(studentChecking);
    }*/
    public StudentChecking createAccount (CheckingDTO studentChecking){
        accountHolderRepository.findById(studentChecking.getPrimaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Primary account holder not found"));
        Money balance = studentChecking.getBalance();
        String secretKey = studentChecking.getSecretKey();
        AccountHolder primaryOwner = accountHolderRepository.findById(studentChecking.getPrimaryOwnerId()).get();
        StudentChecking studentCheckingAccount = new StudentChecking(balance,primaryOwner,secretKey);
        if(studentChecking.getSecondaryOwnerId()!= null){
            accountHolderRepository.findById(studentChecking.getSecondaryOwnerId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Secondary account holder not found"));
            AccountHolder secondaryOwner = accountHolderRepository.findById(studentChecking.getSecondaryOwnerId()).get();
            studentCheckingAccount.setSecondaryOwner(secondaryOwner);
        }
        //studentCheckingAccount.setSecretKey(passwordEncoder.encode(studentChecking.getSecretKey()));
        studentCheckingAccount.setType(Type.STUDENT_CHECKING);
        return studentCheckingRepository.save(studentCheckingAccount);
    }
}
