package com.group2.androidbankingapp.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;

public class AddressFragment extends Fragment {
    View rootView;
    ImageView returnBack;
    TextView returnBack2, homeAddressValueTV, mailingAddressValueTV;

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


        return rootView;
    }

    public void returnBackClickHandler(){
        getParentFragmentManager().popBackStack();
    }

    public void onRBCInsuranceNumClickHandler() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8886447581")));
    }
}