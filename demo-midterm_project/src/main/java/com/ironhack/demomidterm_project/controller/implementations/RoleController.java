package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.RoleControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.RoleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleController implements RoleControllerInterface {
    @Autowired
    private RoleServiceInterface roleServiceInterface;
}
