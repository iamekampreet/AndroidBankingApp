package com.group2.androidbankingapp.splitbill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.common.AccountInfoModel;
import com.group2.androidbankingapp.common.AccountType;

public class AccountModalBottomSheet extends BottomSheetDialogFragment {

    OnAccountSelectedListener listener;

    AccountModalBottomSheet(OnAccountSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_account_modal_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView closeImageView = view.findViewById(R.id.imageView_close);
        CardView checking = view.findViewById(R.id.cardview_checking);
        CardView saving =view.findViewById(R.id.cardview_saving);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    AccountInfoModel accountInfoModel = new AccountInfoModel();
                    accountInfoModel.setAccountType(AccountType.CHECKING);
                    accountInfoModel.setAccountNumber(1234);
                    accountInfoModel.setAccountBalance(123456.0);
                    listener.onAccountSelected(accountInfoModel);
                }
                dismiss();
            }
        });

        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    AccountInfoModel accountInfoModel = new AccountInfoModel();
                    accountInfoModel.setAccountType(AccountType.SAVING );
                    accountInfoModel.setAccountNumber(9876);
                    accountInfoModel.setAccountBalance(98765.0);
                    listener.onAccountSelected(accountInfoModel);
                }
                dismiss();
            }
        });
    }
}

interface OnAccountSelectedListener {
    void onAccountSelected(AccountInfoModel accountInfoModel);
}
