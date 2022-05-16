package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.repository.StudentCheckingRepository;
import com.ironhack.demomidterm_project.service.interfaces.StudentCheckingServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentCheckingService implements StudentCheckingServiceInterface {
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
}
