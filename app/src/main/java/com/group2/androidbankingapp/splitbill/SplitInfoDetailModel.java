package com.group2.androidbankingapp.splitbill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.androidbankingapp.models.AccountInfo;

import org.parceler.Parcel;

import java.util.List;

@Parcel
@JsonIgnoreProperties(value = { "_id" })
public class SplitInfoDetailModel {
    List<ContactModel> friendInfos;

    String note;

    @JsonProperty("accountNumber")
    AccountInfo depositInfo;

    String userEmail;

    public SplitInfoDetailModel() {

    }

    public List<ContactModel> getFriendInfos() {
        return friendInfos;
    }

    public void setFriendInfos(List<ContactModel> friendInfos) {
        this.friendInfos = friendInfos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AccountInfo getDepositInfo() {
        return depositInfo;
    }

    public void setDepositInfo(AccountInfo depositInfo) {
        this.depositInfo = depositInfo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
