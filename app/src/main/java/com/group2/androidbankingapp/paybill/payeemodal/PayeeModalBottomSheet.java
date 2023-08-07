package com.group2.androidbankingapp.paybill.payeemodal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.paybill.AddPayeeFragment;

public class PayeeModalBottomSheet extends BottomSheetDialogFragment {

    OnPayeeSelectedListener listener;

    public PayeeModalBottomSheet(OnPayeeSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_payee_modal_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView closeImageView = view.findViewById(R.id.imageView_close);
        RecyclerView recyclerViewAccounts = view.findViewById(R.id.recyclerView_account);
        PayeeModalAdapter adapter = new PayeeModalAdapter(listener, this);
        recyclerViewAccounts.setAdapter(adapter);
        recyclerViewAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAccounts.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        Button addPayeeButton = view.findViewById(R.id.button_add_payee);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addPayeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, AddPayeeFragment.class, null)
                        .addToBackStack(MoveMoneyFragment.TAG)
                        .commit();
                dismiss();
            }
        });
    }
}
