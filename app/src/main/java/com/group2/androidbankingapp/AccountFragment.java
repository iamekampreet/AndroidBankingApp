package com.group2.androidbankingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.models.CardInfoModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    View rootView;
    ImageView returnBack;
    TextView chkActNumTV, chkActBalTV, savActNumTV, savActBalTV, bankingTotalTV, creditCardAccNumTV, creditCardValTV, cardsTotalTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        UserModel user = Singleton.getInstance().user;
        ArrayList<AccountInfo> accounts = user.getAccounts();
        ArrayList<CardInfoModel>  cards = user.getCards();

        chkActNumTV = rootView.findViewById(R.id.chkActNumTV);
        chkActNumTV.setText(Long.toString(accounts.get(0).getAccountNumber()));

        double chkActBal = accounts.get(0).getAccountBalance();
        chkActBalTV = rootView.findViewById(R.id.chkActBalTV);
        chkActBalTV.setText("CAD " + Double.toString(chkActBal));

        savActNumTV = rootView.findViewById(R.id.savActNumTV);
        savActNumTV.setText(Long.toString(accounts.get(1).getAccountNumber()));

        double savActBal = accounts.get(1).getAccountBalance();
        savActBalTV = rootView.findViewById(R.id.savActBalTV);
        savActBalTV.setText("CAD " + Double.toString(savActBal));

        bankingTotalTV = rootView.findViewById(R.id.bankingTotalTV);
        bankingTotalTV.setText("CAD " + Double.toString(chkActBal + savActBal));

        creditCardAccNumTV = rootView.findViewById(R.id.creditCardAccNumTV);
        creditCardAccNumTV.setText(cards.get(1).getCardNumber());

        double creditCardBal = cards.get(0).getAccountBalance();
        creditCardValTV = rootView.findViewById(R.id.creditCardValTV);
        creditCardValTV.setText("CAD " + Double.toString(creditCardBal));

        cardsTotalTV = rootView.findViewById(R.id.cardsTotalTV);
        cardsTotalTV.setText("CAD "  + Double.toString(creditCardBal));

        return rootView;
    }

    public void returnBackClickHandler() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}