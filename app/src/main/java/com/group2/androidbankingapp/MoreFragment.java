package com.group2.androidbankingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.profile.ProfileFragment;

public class MoreFragment extends Fragment {
    public static final String TAG = "MORE_FRAGMENT_TAG";
    ImageView profileIcon, returnBack;
    TextView profileTV;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_more, container, false);

        profileIcon = rootView.findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> profileClickHandler());

        profileTV = rootView.findViewById(R.id.profileTV);
        profileTV.setOnClickListener(v -> profileClickHandler());

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        return rootView;
    }

    public void profileClickHandler(){
        ProfileFragment profileFragment = new ProfileFragment();

        getParentFragmentManager().beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .addToBackStack(MoreFragment.TAG)
                .commit();
    }

    public void returnBackClickHandler() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}