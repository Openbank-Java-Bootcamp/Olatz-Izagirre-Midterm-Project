package com.ironhack.demomidterm_project.service.interfaces;

import com.ironhack.demomidterm_project.model.User;

import java.util.List;

public interface UserServiceInterface {
    void deleteUser (Long id);

    List<User> getUsers();
    User getUser (Long id);
}
