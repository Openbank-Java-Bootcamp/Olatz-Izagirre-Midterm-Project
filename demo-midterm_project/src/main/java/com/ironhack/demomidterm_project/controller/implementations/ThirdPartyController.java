package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.controller.interfaces.ThirdPartyControllerInterface;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.service.interfaces.ThirdPartyServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ThirdPartyController implements ThirdPartyControllerInterface {
    @Autowired
    private ThirdPartyServiceInterface thirdPartyServiceInterface;

    @PostMapping("/users/thirdParties")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty (@RequestBody @Valid ThirdParty thirdParty){
        return thirdPartyServiceInterface.createThirdParty(thirdParty);
    }

    @GetMapping("/users/thirdParties")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> getThirdParties() {
        return thirdPartyServiceInterface.getThirdParties();
    }

    @DeleteMapping("/users/thirdParties/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        thirdPartyServiceInterface.deleteUser(id);
    }
}
