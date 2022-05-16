package com.ironhack.demomidterm_project.controller.implementations;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.controller.interfaces.UserControllerInterface;
import com.ironhack.demomidterm_project.model.AccountHolder;
import com.ironhack.demomidterm_project.model.Admin;
import com.ironhack.demomidterm_project.model.ThirdParty;
import com.ironhack.demomidterm_project.model.User;
import com.ironhack.demomidterm_project.service.interfaces.AccountHolderServiceInterface;
import com.ironhack.demomidterm_project.service.interfaces.AdminServiceInterface;
import com.ironhack.demomidterm_project.service.interfaces.ThirdPartyServiceInterface;
import com.ironhack.demomidterm_project.service.interfaces.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserController implements UserControllerInterface {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private AdminServiceInterface adminServiceInterface;

    @Autowired
    private AccountHolderServiceInterface accountHolderServiceInterface;
    @Autowired
    private ThirdPartyServiceInterface thirdPartyServiceInterface;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        if (user instanceof Admin) {
            return adminServiceInterface.createAdmin((Admin) user);
        } else if (user instanceof AccountHolder) {
            return accountHolderServiceInterface.createAccountHolder((AccountHolder) user);
        } else {
            return thirdPartyServiceInterface.createThirdParty((ThirdParty) user);
        }
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userServiceInterface.deleteUser(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userServiceInterface.getUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id) {
        return userServiceInterface.getUser(id);
    }

    @PatchMapping("/users/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordChange(@PathVariable String username, Principal principal, @RequestBody UserPasswordOnlyDTO userPasswordOnlyDTO) {
        if (Objects.equals(principal.getName(), username)) {
            userServiceInterface.updatePassword(username, userPasswordOnlyDTO.getPassword());
        }
    }
}
