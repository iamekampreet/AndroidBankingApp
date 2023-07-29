package com.group2.androidbankingapp.splitbill;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public class ContactModel implements Comparable<ContactModel>, Parcelable {

    private String name;
    private String phoneNumber;

    private double amount;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ContactModel() {

    }

    @Override
    public int compareTo(ContactModel contactModel) {
        return this.name.compareTo(contactModel.name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Name = " + name + ", Phone Number = " + phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phoneNumber);
    }

    private ContactModel(Parcel in) {
        name = in.readString();
        phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<ContactModel> CREATOR
            = new Parcelable.Creator<ContactModel>() {

        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source) ;
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
