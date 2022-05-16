package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.model.CheckingAccount;
import com.ironhack.demomidterm_project.model.StudentChecking;
import com.ironhack.demomidterm_project.repository.StudentCheckingRepository;
import com.ironhack.demomidterm_project.service.interfaces.StudentCheckingServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class StudentCheckingService implements StudentCheckingServiceInterface {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentChecking createAccount (StudentChecking studentChecking){
        if (studentChecking.getId() != null){
            Optional<StudentChecking> optionalStudentChecking = studentCheckingRepository.findById(studentChecking.getId());
            if(optionalStudentChecking.isPresent()) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Account already exists");
        }
        studentChecking.setSecretKey(passwordEncoder.encode(studentChecking.getSecretKey()));
        return studentCheckingRepository.save(studentChecking);
    }
}
