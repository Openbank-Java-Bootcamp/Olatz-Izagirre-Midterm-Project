package com.ironhack.demomidterm_project.DTO;

import com.ironhack.demomidterm_project.utils.Money;
import jakarta.validation.constraints.NotNull;

public class ThirdPartyTransferDTO {
    @NotNull
    private Money amount;
    @NotNull
    private Long accountId;
    @NotNull
    private String secretKey;
    @NotNull
    private String hashedKey;

    public ThirdPartyTransferDTO() {
    }

    public ThirdPartyTransferDTO(Money amount, Long accountId, String secretKey,String hashedKey) {
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
        this.hashedKey = hashedKey;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
