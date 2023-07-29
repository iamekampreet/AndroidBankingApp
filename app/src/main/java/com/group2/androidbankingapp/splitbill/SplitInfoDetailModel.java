package com.group2.androidbankingapp.splitbill;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.group2.androidbankingapp.common.AccountInfoModel;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SplitInfoDetailModel {
    List<ContactModel> friendInfos;

    String note;

    AccountInfoModel depositInfo;

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

    public AccountInfoModel getDepositInfo() {
        return depositInfo;
    }

    public void setDepositInfo(AccountInfoModel depositInfo) {
        this.depositInfo = depositInfo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
