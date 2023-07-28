package com.group2.androidbankingapp.splitbill;

import androidx.annotation.NonNull;

public class ContactRowModel {
    boolean isSectionIndicator = false;
    String sectionName;
    ContactModel data;

    boolean isSelected;

    public ContactRowModel(boolean isSectionIndicator, String sectionName, ContactModel data) {
        this.isSectionIndicator = isSectionIndicator;
        this.sectionName = sectionName;
        this.data = data;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @NonNull
    @Override
    public String toString() {
        if (isSectionIndicator) {
            return sectionName + "\n";
        } else {
            return data.toString() + "\n";
        }
    }
}
