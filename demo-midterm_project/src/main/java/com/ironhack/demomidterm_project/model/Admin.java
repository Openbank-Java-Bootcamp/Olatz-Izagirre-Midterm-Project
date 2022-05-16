package com.ironhack.demomidterm_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn (name = "id")
public class Admin extends User{

    public Admin() {
    }

    public Admin(String name, String username) {
        super(name, username);
    }
}
