package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.DTO.UserPasswordOnlyDTO;
import com.ironhack.demomidterm_project.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.List;

public interface UserControllerInterface {
    void deleteUser (Long id);

    User getUser (Long id);

    List<User> getUsers ();

    void passwordChange (String username, Principal principal, UserPasswordOnlyDTO userPasswordOnlyDTO);
}
