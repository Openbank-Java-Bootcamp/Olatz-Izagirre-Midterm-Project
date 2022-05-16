package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.StudentCheckingControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.StudentCheckingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StudentCheckingController implements StudentCheckingControllerInterface {
    @Autowired
    private StudentCheckingServiceInterface studentCheckingServiceInterface;
}
