package com.example.jsonObjects;

public class Account {
    private String accountCode;
    private String bicCode;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getBicCode() {
        return bicCode;
    }

    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountCode='" + accountCode + '\'' +
                ", bicCode='" + bicCode + '\'' +
                '}';
    }
}
