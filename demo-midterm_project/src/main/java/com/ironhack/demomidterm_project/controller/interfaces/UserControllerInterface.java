package com.ironhack.demomidterm_project.controller.interfaces;

import com.ironhack.demomidterm_project.model.User;

import java.util.List;

public interface UserControllerInterface {
    User createUser(User user);
    void deleteUser (Long id);

    User getUser (Long id);

    List<User> getUsers ();
}
