package com.ironhack.demomidterm_project.service.implementation;

import com.ironhack.demomidterm_project.repository.SavingsRepository;
import com.ironhack.demomidterm_project.service.interfaces.SavingsServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SavingsService implements SavingsServiceInterface {
    @Autowired
    private SavingsRepository savingsRepository;
}
