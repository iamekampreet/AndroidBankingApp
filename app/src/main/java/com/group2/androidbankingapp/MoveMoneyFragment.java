package com.group2.androidbankingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group2.androidbankingapp.splitbill.SplitBillActivity;

public class MoveMoneyFragment extends Fragment {
    View transferBetweenAccounts;
    View returnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_move_money, container, false);
        setInteracItalic(rootView);

        transferBetweenAccounts = rootView.findViewById(R.id.constraint_transfer_between_my_accounts);
        returnBack = rootView.findViewById(R.id.img_return_back);
        transferBetweenAccounts.setOnClickListener(v -> transferBetweenAccountsClick());

        returnBack.setOnClickListener(v -> returnBackClick());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstraintLayout splitBill = view.findViewById(R.id.constraint_split_with_friends);
        splitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent splitBillIntent =
                        new Intent(MoveMoneyFragment.this.getActivity(), SplitBillActivity.class);
                startActivity(splitBillIntent);
            }
        });

    }

    private void setInteracItalic(View rootView) {
        TextView tvSendMoneyInterac = rootView.findViewById(R.id.tv_send_money_with_interac_e_transfer);
        String fullText = "Send Money with Interac e-Transfer";

        // Create a SpannableString from the full text
        SpannableString spannableString = new SpannableString(fullText);

        // Find the start and end indices of the word "Interac"
        int startIndex = fullText.indexOf("Interac");
        int endIndex = startIndex + "Interac".length();

        // Apply italic style to the word "Interac"
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the modified SpannableString as the text of the TextView
        tvSendMoneyInterac.setText(spannableString);
    }
    private void transferBetweenAccountsClick(){
        TransferBetweenAccountsFragment transferFragment = new TransferBetweenAccountsFragment();

        FragmentManager fragmentManager = getParentFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, transferFragment)
                .addToBackStack(null)
                .commit();
    }

    private void returnBackClick(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}