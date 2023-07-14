package com.group2.androidbankingapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

public class TransferBetweenAccountsFragment extends Fragment {
    private PopupWindow popupWindow;
    private LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfer_between_my_accounts, container, false);
        this.inflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgFromDropdown = view.findViewById(R.id.img_from_dropdown);
        imgFromDropdown.setOnClickListener(v -> showPopupWindow(v));
    }

    private void showPopupWindow(View anchorView) {
        View popupView = inflater.inflate(R.layout.from_popup_layout, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);

        ImageView close = popupView.findViewById(R.id.img_close_popup);
        close.setOnClickListener(popupView1 -> popupWindow.dismiss());

    }
}