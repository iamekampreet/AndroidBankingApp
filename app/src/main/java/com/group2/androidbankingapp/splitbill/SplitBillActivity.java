package com.group2.androidbankingapp.splitbill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.group2.androidbankingapp.HomeFragment;
import com.group2.androidbankingapp.R;

public class SplitBillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        Button pickFriendsButton = findViewById(R.id.button_pick_friends);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        pickFriendsButton.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_split_bill, PickFriendsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            appBarLayout.setExpanded(false);
        });
    }
}