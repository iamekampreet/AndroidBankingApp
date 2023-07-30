package com.group2.androidbankingapp.splitbill.accountModal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.group2.androidbankingapp.R;

public class AccountModalBottomSheet extends BottomSheetDialogFragment {

    OnAccountSelectedListener listener;

    public AccountModalBottomSheet(OnAccountSelectedListener listener) {
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
        RecyclerView recyclerViewAccounts = view.findViewById(R.id.recyclerView_account);
        AccountModalAdapter adapter = new AccountModalAdapter(listener, this);
        recyclerViewAccounts.setAdapter(adapter);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAccounts.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
