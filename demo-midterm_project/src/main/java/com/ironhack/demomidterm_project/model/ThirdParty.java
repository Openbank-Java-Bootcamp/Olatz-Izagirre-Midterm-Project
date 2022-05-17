package com.ironhack.demomidterm_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn (name = "id")
public class ThirdParty extends User{

    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name, String username) {
        super(name, username);
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
