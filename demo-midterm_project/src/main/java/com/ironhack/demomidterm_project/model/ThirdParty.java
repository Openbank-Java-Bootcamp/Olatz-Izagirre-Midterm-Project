package com.ironhack.demomidterm_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn (name = "id")
public class ThirdParty extends User{
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer hashedKey;

    public ThirdParty(String name, String username, String password) {
        super(name, username);
    }

    public Integer getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(Integer hashedKey) {
        this.hashedKey = hashedKey;
    }
}
