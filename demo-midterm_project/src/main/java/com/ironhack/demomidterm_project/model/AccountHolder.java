package com.ironhack.demomidterm_project.model;

import com.ironhack.demomidterm_project.utils.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


import java.util.Date;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{
    @Past
    @NotNull
    private Date dateOfBirth;
    @Embedded
    @NotNull
    private Address primaryAddress;
    @AttributeOverrides({
            @AttributeOverride( name = "streetAddress" , column = @Column(name = "mailing_street")),
            @AttributeOverride( name = "city" , column = @Column(name = "mailing_city")),
            @AttributeOverride( name = "postalCode" , column = @Column(name = "mailing_postal")),

    })
    @Embedded
    private Address mailingAddress;
    @OneToMany (mappedBy = "primaryOwner")
    private List<Account> primaryOwnership;
    @OneToMany (mappedBy = "secondaryOwner")
    private List<Account> secondaryOwnership;

    public AccountHolder() {
    }

    public AccountHolder(String name, String username, Date dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, username);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String name, String username, Date dateOfBirth, Address primaryAddress) {
        super(name, username);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
