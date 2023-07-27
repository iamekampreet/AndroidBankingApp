package com.group2.androidbankingapp.splitbill;

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
}
