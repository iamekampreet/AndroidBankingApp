package com.group2.androidbankingapp.models;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.group2.androidbankingapp.utils.Utils;

import org.parceler.Parcel;

@Parcel
public class AccountInfo {
    String _id;
    @JsonValue()
    long accountNumber;
    int accountType;
    int status;

    double accountBalance;

    public AccountInfo() { }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return Utils.getAccounttype(accountType) + "(" + accountNumber + ")";
    }
}
