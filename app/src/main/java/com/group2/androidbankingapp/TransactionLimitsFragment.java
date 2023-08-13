package com.group2.androidbankingapp;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;


public class TransactionLimitsFragment extends Fragment {

    View rootView;
    ConstraintLayout parentCL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_transaction_limits, container, false);

        parentCL = rootView.findViewById(R.id.parentCL);
        parentCL.setOnClickListener(v -> returnBackClickHandler());

        return rootView;
    }

    public void returnBackClickHandler(){
        getParentFragmentManager().popBackStack();
    }

}