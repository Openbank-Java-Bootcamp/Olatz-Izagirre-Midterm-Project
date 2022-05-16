package com.ironhack.demomidterm_project.utils;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Embeddable
public class Address {
    @NotNull
    private String streetAddress;
    @NotNull
    private String city;
    @Digits(integer = 5,fraction = 0)
    @Positive
    @NotNull
    private Integer postalCode;

    public Address(String streetAddress, String city, Integer postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }
}
