package com.group2.androidbankingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AccountFragment extends Fragment {
    View rootView;
    ImageView returnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        return rootView;
    }

    public void returnBackClickHandler() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}