package com.group2.androidbankingapp.paybill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.models.PayeeModel;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayBillModel {

    String fromUserId;
    @JsonProperty("from")
    AccountInfo accountFrom;
    @JsonProperty("to")
    PayeeModel accountTo;

    double amount;

    @JsonProperty("date")
    Date when;

    /**
     * 0 - Once
     * 1 - Weekly
     * 2 - Monthly
     */
    int frequency;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public AccountInfo getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(AccountInfo accountFrom) {
        this.accountFrom = accountFrom;
    }

    public PayeeModel getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(PayeeModel accountTo) {
        this.accountTo = accountTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isValid() {
        return (accountFrom != null)
                && (accountTo != null)
                && amount > 0
                && when != null;
    }
}
