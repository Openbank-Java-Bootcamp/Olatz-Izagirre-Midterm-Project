package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.controller.interfaces.AccountHolderControllerInterface;
import com.ironhack.demomidterm_project.service.interfaces.AccountHolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountHolderController implements AccountHolderControllerInterface {
    @Autowired
    private AccountHolderServiceInterface accountHolderServiceInterface;

    /*@PatchMapping ("/accountHolders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordChange (@PathVariable Long id, @RequestBody UserPasswordOnlyDTO userPasswordOnlyDTO){
        accountHolderServiceInterface.passwordChange(id,userPasswordOnlyDTO);
    }*/

}
