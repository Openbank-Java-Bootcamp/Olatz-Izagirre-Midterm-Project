package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.AdminControllerInterface;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.service.interfaces.AdminServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminController implements AdminControllerInterface {
    @Autowired
    private AdminServiceInterface adminServiceInterface;

    @PostMapping("/users/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin (@RequestBody @Valid Admin admin){
        return adminServiceInterface.createAdmin(admin);
    }


}
