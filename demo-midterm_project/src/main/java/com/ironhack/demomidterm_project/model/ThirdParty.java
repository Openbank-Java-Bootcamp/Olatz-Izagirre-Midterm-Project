package com.ironhack.demomidterm_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn (name = "id")
public class ThirdParty extends User{

    @NotNull
    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name, String username, String hashedKey) {
        super(name, username);
        this.hashedKey = hashedKey;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

}
