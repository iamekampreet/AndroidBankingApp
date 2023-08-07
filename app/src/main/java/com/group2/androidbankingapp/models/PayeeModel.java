package com.group2.androidbankingapp.models;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayeeModel {
    String payeeId;

    String displayName;

    String description;

    int accountNumber;

    public PayeeModel() {
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return displayName + "(" + accountNumber + ")";
    }
}
