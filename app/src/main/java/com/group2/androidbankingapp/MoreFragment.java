package com.group2.androidbankingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.profile.ProfileFragment;
import com.group2.androidbankingapp.utils.SessionManager;

public class MoreFragment extends Fragment {
    public static final String TAG = "MORE_FRAGMENT_TAG";
    ImageView profileIcon, returnBack, signOutIcon;
    TextView profileTV, signOutTV;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_more, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        profileIcon = rootView.findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> profileClickHandler());

        profileTV = rootView.findViewById(R.id.profileTV);
        profileTV.setOnClickListener(v -> profileClickHandler());

        signOutIcon = rootView.findViewById(R.id.signOutIcon);
        signOutIcon.setOnClickListener(v -> signOutClickHandler());

        signOutTV = rootView.findViewById(R.id.signOutTV);
        signOutTV.setOnClickListener(v -> signOutClickHandler());

        return rootView;
    }

    public void profileClickHandler(){
        ProfileFragment profileFragment = new ProfileFragment();

        getParentFragmentManager().beginTransaction()
                .add(R.id.fragment_container, profileFragment)
                .addToBackStack(MoreFragment.TAG)
                .commit();
    }

    public void signOutClickHandler(){
        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.removeAuthToken();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void returnBackClickHandler() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}