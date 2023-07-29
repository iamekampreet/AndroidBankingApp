package com.group2.androidbankingapp.common;

import androidx.annotation.NonNull;

import org.parceler.Parcel;

import java.math.BigDecimal;

@Parcel
public class AccountInfoModel {
    AccountType accountType;

    long accountNumber;

    double accountBalance;

    public AccountInfoModel() {

    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @NonNull
    @Override
    public String toString() {
        return accountType.toString() + "(" + accountNumber + ")" ;
    }
}
