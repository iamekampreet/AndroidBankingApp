package com.group2.androidbankingapp.paybill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.paybill.scheduledpayments.UpcomingPaymentsFragment;
import com.group2.androidbankingapp.splitbill.SplitAccountInfoFragment;
import com.group2.androidbankingapp.splitbill.accountModal.AccountModalBottomSheet;
import com.group2.androidbankingapp.splitbill.accountModal.OnAccountSelectedListener;

public class PayBillFragment extends Fragment {

    public PayBillFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        OptionRowItem payBillConstraint = view.findViewById(R.id.pay_bill);
        payBillConstraint.setOnContainerClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, PayBillInputFragment.class, null)
                        .addToBackStack(MoveMoneyFragment.TAG)
                        .commit();
            }
        });

        OptionRowItem addPayeeConstraint = view.findViewById(R.id.add_a_payee);
        addPayeeConstraint.setOnContainerClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, AddPayeeFragment.class, null)
                        .addToBackStack(MoveMoneyFragment.TAG)
                        .commit();
            }
        });

        OptionRowItem upcomingPaymentConstraint = view.findViewById(R.id.view_upcoming_payment);
        upcomingPaymentConstraint.setOnContainerClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, UpcomingPaymentsFragment.class, null)
                        .addToBackStack(MoveMoneyFragment.TAG)
                        .commit();
            }
        });

    }
}