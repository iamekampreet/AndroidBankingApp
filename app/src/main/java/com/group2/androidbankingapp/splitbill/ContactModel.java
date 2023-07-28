package com.group2.androidbankingapp.splitbill;

import androidx.annotation.NonNull;

public class ContactModel implements Comparable<ContactModel> {

    private String name;
    private String phoneNumber;

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

    @Override
    public int compareTo(ContactModel contactModel) {
        return this.name.compareTo(contactModel.name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Name = " + name + ", Phone Number = " + phoneNumber;
    }
}
