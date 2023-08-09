package com.group2.androidbankingapp.profile;

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

public class PhoneFragment extends Fragment {

    View rootView;
    ImageView returnBack;
    TextView returnBack2, homePrimaryPhoneValueTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_phone, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        returnBack2 = rootView.findViewById(R.id.returnBack2);
        returnBack2.setOnClickListener(v -> returnBackClickHandler());

        UserModel user = Singleton.getInstance().user;

        homePrimaryPhoneValueTV = rootView.findViewById(R.id.homePrimaryPhoneValueTV);
        homePrimaryPhoneValueTV.setText(user.getPhone());

        return rootView;
    }

    public void returnBackClickHandler(){
        getParentFragmentManager().popBackStack();
    }
}