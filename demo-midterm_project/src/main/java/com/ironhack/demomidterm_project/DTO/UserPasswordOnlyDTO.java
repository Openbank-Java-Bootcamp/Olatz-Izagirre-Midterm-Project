package com.ironhack.demomidterm_project.DTO;

import jakarta.validation.constraints.Digits;

public class UserPasswordOnlyDTO {
    //@Digits(integer = 6,fraction = 0)
    private String password;

    public UserPasswordOnlyDTO() {
    }

    public UserPasswordOnlyDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
