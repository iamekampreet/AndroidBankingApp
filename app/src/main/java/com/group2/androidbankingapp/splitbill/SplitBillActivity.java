package com.group2.androidbankingapp.splitbill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.group2.androidbankingapp.HomeFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.utils.Utils;

import java.math.BigDecimal;

public class SplitBillActivity extends AppCompatActivity {

    public static final String ROOT = "SplitBillActivity";

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    TextInputEditText splitAmountEditText;

    ConstraintLayout initialContainer;
    ConstraintLayout summaryContainer;

    TextView totalRequestAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        Button pickFriendsButton = findViewById(R.id.button_pick_friends);
        splitAmountEditText = findViewById(R.id.editText_split_amount);
        CoordinatorLayout mainLayout = findViewById(R.id.main_container);

        appBarLayout = findViewById(R.id.appbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        initialContainer = findViewById(R.id.container_initial);
        summaryContainer = findViewById(R.id.container_summary);
        totalRequestAmountTextView = findViewById(R.id.textView_amount);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickFriendsButton.setOnClickListener(view -> {
            enableExpandableAppBar();
            appBarLayout.setExpanded(false);

            PickFriendsFragment pickFriendsFragment =
                    (PickFriendsFragment) getSupportFragmentManager().findFragmentByTag(PickFriendsFragment.TAG);
            if (pickFriendsFragment == null) {
                pickFriendsFragment = new PickFriendsFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_split_bill, pickFriendsFragment, PickFriendsFragment.TAG)
                    .setReorderingAllowed(true)
                    .addToBackStack(SplitBillActivity.ROOT)
                    .commit();

            Utils.hideKeyboard(this);
        });

        splitAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String userInput = editable.toString();
                Snackbar snackbar = Snackbar.make(mainLayout,
                        "Please enter an amount between $0.01 and $250,000.",
                        Snackbar.LENGTH_LONG);
                try {
                    if (userInput.isEmpty()) return;
                    float splitAmount = Float.parseFloat(userInput);
                    if (splitAmount >= 0.01 && splitAmount <= 250000) {
                        pickFriendsButton.setVisibility(View.VISIBLE);
                    } else {
                        pickFriendsButton.setVisibility(View.INVISIBLE);
                        if (!snackbar.isShown()) {
                            snackbar.setAction(R.string.action_text, view -> {
                                snackbar.dismiss();
                            }).show();
                        }
                    }
                } catch (Exception ex) {
                    Snackbar.make(mainLayout, "Invalid Input", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
                int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 1) {
                    SplitBillActivity.this.setAppBarHeight(
                            getResources().getDimensionPixelSize(R.dimen.app_bar_height_full)
                    );
                    SplitBillActivity.this.setSummaryAppBarVisibility(View.GONE);
                    appBarLayout.setExpanded(true);
                } else if (count == 0) {
                    SplitBillActivity.this.finish();
                }
            }
        });
    }

    public void disableExpandableAppBar() {
        CoordinatorLayout.LayoutParams layoutParamsAppBar =
            (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParamsAppBar.height = getResources().getDimensionPixelSize(R.dimen.app_bar_collapsed_height);
        appBarLayout.setExpanded(false);
    }

    public double getSplitAmount() {
        String splitAmountStr = splitAmountEditText.getText().toString();
        return Double.parseDouble(splitAmountStr);
    }

    public void enableExpandableAppBar() {
        AppBarLayout.LayoutParams layoutParams =
                (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
    }

    public void setAppBarHeight(int height) {
        CoordinatorLayout.LayoutParams layoutParamsAppBar =
                (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParamsAppBar.height = height;
    }

    public void setSummaryAppBarVisibility(int visibility) {

        if (visibility == View.VISIBLE)  {
            initialContainer.setVisibility(View.GONE);
            summaryContainer.setVisibility(View.VISIBLE);
        } else {
            initialContainer.setVisibility(View.VISIBLE);
            summaryContainer.setVisibility(View.GONE);
        }

        appBarLayout.setExpanded(true);
    }

    public void updateTotalRequestAmount(double amount) {
        totalRequestAmountTextView.setText(amount + "");
    }
}