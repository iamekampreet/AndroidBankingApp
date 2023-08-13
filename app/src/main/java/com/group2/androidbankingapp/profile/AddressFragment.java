package com.group2.androidbankingapp.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.MainActivity;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;

public class AddressFragment extends Fragment {
    View rootView;
    ImageView returnBack;
    TextView returnBack2, homeAddressValueTV, mailingAddressValueTV, insuranceNumTV, investmentNumTV, directInvestingNumTV, dominionServicesNumTV, wealthMgmtNumTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_address, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        returnBack2 = rootView.findViewById(R.id.returnBack2);
        returnBack2.setOnClickListener(v -> returnBackClickHandler());

        UserModel user = Singleton.getInstance().user;

        homeAddressValueTV = rootView.findViewById(R.id.homeAddressValueTV);
        homeAddressValueTV.setText(user.getAddress());

        mailingAddressValueTV = rootView.findViewById(R.id.mailingAddressValueTV);
        mailingAddressValueTV.setText(user.getAddress());

        insuranceNumTV = rootView.findViewById(R.id.insuranceNumTV);
        insuranceNumTV.setOnClickListener(v -> onInsuranceNumClickHandler());

        investmentNumTV = rootView.findViewById(R.id.investmentsNumTV);
        investmentNumTV.setOnClickListener(v -> onInvestmentNumClickHandler());

        directInvestingNumTV = rootView.findViewById(R.id.directInvestingNumTV);
        directInvestingNumTV.setOnClickListener(v -> onDirectInvestingNumClickHandler());

        dominionServicesNumTV = rootView.findViewById(R.id.dominionServicesNumTV);
        dominionServicesNumTV.setOnClickListener(v -> onDominionServicesNumClickHandler());

        wealthMgmtNumTV = rootView.findViewById(R.id.wealthMgmtNumTV);
        wealthMgmtNumTV.setOnClickListener(v -> onWealthMgmtNumClickHandler());


        return rootView;
    }

    public void returnBackClickHandler(){
        getParentFragmentManager().popBackStack();
    }

    public void onInsuranceNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1-888-644-7581")));
    }

    public void onInvestmentNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1-800-463-3863")));
    }


    public void onDirectInvestingNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1-800-769-2560")));
    }


    public void onDominionServicesNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1-888-820-8006")));
    }


    public void onWealthMgmtNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:1-833-654-2566")));
    }
}