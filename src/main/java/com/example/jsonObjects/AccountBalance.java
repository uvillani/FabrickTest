package com.example.jsonObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccountBalance implements Serializable {

    @JsonProperty("date")
    private String date;

    @JsonProperty("balance")
    private String balance;

    @JsonProperty("availableBalance")
    private String availableBalance;

    @JsonProperty("currency")
    private String currency;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
