package com.group2.androidbankingapp.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group2.androidbankingapp.MoreFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;

public class ProfileFragment extends Fragment {
    View rootView;
    ImageView returnBack, chevronManagePhoneIcon, chevronManageEmailIcon, chevronManageAddressIcon;
    TextView returnBack2, managePhoneTV, manageEmailTV, manageAddressTV, userNameTV, homeAddressValTV, mailingAddressValTV, homePrimaryPhoneValTV, emailPrimaryValTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        returnBack = rootView.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(v -> returnBackClickHandler());

        returnBack2 = rootView.findViewById(R.id.returnBack2);
        returnBack2.setOnClickListener(v -> returnBackClickHandler());

        managePhoneTV = rootView.findViewById(R.id.managePhoneTV);
        managePhoneTV.setOnClickListener(v -> managePhoneClickHandler());

        chevronManagePhoneIcon = rootView.findViewById(R.id.chevronManagePhoneIcon);
        chevronManagePhoneIcon.setOnClickListener(v -> managePhoneClickHandler());

        manageEmailTV = rootView.findViewById(R.id.manageEmailTV);
        manageEmailTV.setOnClickListener(v -> manageEmailClickHandler());

        chevronManageEmailIcon = rootView.findViewById(R.id.chevronManageEmailIcon);
        chevronManageEmailIcon.setOnClickListener(v -> manageEmailClickHandler());

        manageAddressTV = rootView.findViewById(R.id.manageAddressTV);
        manageAddressTV.setOnClickListener(v -> manageAddressClickHandler());

        chevronManageAddressIcon = rootView.findViewById(R.id.chevronManageAddressIcon);
        chevronManageAddressIcon.setOnClickListener(v -> manageAddressClickHandler());

        UserModel user = Singleton.getInstance().user;

        userNameTV = rootView.findViewById(R.id.userNameTV);
        userNameTV.setText(user.getDisplayName());

        homeAddressValTV = rootView.findViewById(R.id.homeAddressValTV);
        homeAddressValTV.setText(user.getAddress());

        mailingAddressValTV = rootView.findViewById(R.id.mailingAddressValTV);
        mailingAddressValTV.setText(user.getAddress());

        homePrimaryPhoneValTV = rootView.findViewById(R.id.homeAddressValueTV);
        homePrimaryPhoneValTV.setText(user.getPhone());

        emailPrimaryValTV = rootView.findViewById(R.id.emailPrimaryValTV);
        emailPrimaryValTV.setText(user.getEmail());

        return rootView;
    }

    public void returnBackClickHandler(){
        getParentFragmentManager().popBackStack();
    }

    public void managePhoneClickHandler(){
        PhoneFragment phoneFragment = new PhoneFragment();

        getParentFragmentManager().beginTransaction()
                .add(R.id.fragment_container, phoneFragment)
                .addToBackStack(MoreFragment.TAG)
                .commit();
    }

    public void manageEmailClickHandler(){
        EmailFragment emailFragment = new EmailFragment();

        getParentFragmentManager().beginTransaction()
                .add(R.id.fragment_container, emailFragment)
                .addToBackStack(MoreFragment.TAG)
                .commit();
    }

    public void manageAddressClickHandler(){
        AddressFragment addressFragment = new AddressFragment();

        getParentFragmentManager().beginTransaction()
                .add(R.id.fragment_container, addressFragment)
                .addToBackStack(MoreFragment.TAG)
                .commit();
    }
}