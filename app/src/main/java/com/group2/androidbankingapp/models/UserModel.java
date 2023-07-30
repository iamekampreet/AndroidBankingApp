package com.group2.androidbankingapp.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {
    String _id;
    String firstName;
    String lastName;
    String email;
    String password;
    String address;

    String phone;
    String sinNumber;
    int accountType;
    String displayName;
    String owners;
    ArrayList<AccountInfo> accounts;
    ArrayList<CardInfoModel> cards;
    ArrayList<PayeeModel> payee;

    public UserModel() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(String sinNumber) {
        this.sinNumber = sinNumber;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }

    public ArrayList<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<AccountInfo> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<CardInfoModel> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CardInfoModel> cards) {
        this.cards = cards;
    }

    public ArrayList<PayeeModel> getPayee() {
        return payee;
    }

    public void setPayee(ArrayList<PayeeModel> payee) {
        this.payee = payee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
