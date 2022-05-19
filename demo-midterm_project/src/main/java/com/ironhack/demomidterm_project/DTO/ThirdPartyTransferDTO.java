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

    public ThirdPartyTransferDTO() {
    }

    public ThirdPartyTransferDTO(Money amount, Long accountId, String secretKey) {
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
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
}
