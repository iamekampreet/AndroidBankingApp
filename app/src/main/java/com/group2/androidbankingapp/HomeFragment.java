package com.group2.androidbankingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group2.androidbankingapp.bottomnav.BottomNavItem;
import com.group2.androidbankingapp.bottomnav.MyBottomNavigationView;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.models.CardInfoModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.splitbill.SplitBillActivity;
import com.group2.androidbankingapp.utils.Singleton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_accViewAll = rootView.findViewById(R.id.btn_accViewAll);
        btn_accViewAll.setOnClickListener(v -> {
            goToOtherNav(container, R.id.accounts);
        });

        Button btn_manageCards = rootView.findViewById(R.id.btn_manageCards);
        btn_manageCards.setOnClickListener(v -> {
            goToOtherNav(container, R.id.accounts);
        });

        Button btn_payCreditWithPoints = rootView.findViewById(R.id.btn_payCreditWithPoints);
        btn_payCreditWithPoints.setOnClickListener(v -> {
            goToOtherNav(container, R.id.move_money);
        });

        Button btn_payCreditNow = rootView.findViewById(R.id.btn_payCreditNow);
        btn_payCreditNow.setOnClickListener(v -> {
            goToOtherNav(container, R.id.move_money);
        });

        ImageButton btn_cdic = rootView.findViewById(R.id.btn_cdic);
        btn_cdic.setOnClickListener(v -> {
            goToCdic();
        });

        manageSplitWithFriends(rootView);
        setupAccounts(rootView);
        setupCards(rootView);

        return rootView;
    }

    private void goToOtherNav(ViewGroup container, int navID){
        View mainView = (View) container.getParent();
        MyBottomNavigationView navView = mainView.findViewById(R.id.myBottomNavigationView);
        BottomNavItem accountsNav = navView.findViewById(navID);
        accountsNav.performClick();
    }

    private void goToCdic(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cdic.ca/"));
        startActivity(browserIntent);
    }

    private void setupAccounts(View rootView){
        TextView tv_accLine1 = rootView.findViewById(R.id.tv_accLine1);
        TextView tv_accLine1Bal = rootView.findViewById(R.id.tv_accLine1Bal);
        ConstraintLayout tv_accLine1Wrapper = rootView.findViewById(R.id.tv_accLine1Wrapper);
        TextView tv_accLine2 = rootView.findViewById(R.id.tv_accLine2);
        TextView tv_accLine2Bal = rootView.findViewById(R.id.tv_accLine2Bal);
        ConstraintLayout tv_accLine2Wrapper = rootView.findViewById(R.id.tv_accLine2Wrapper);
        TextView tv_accLine3 = rootView.findViewById(R.id.tv_accLine3);
        TextView tv_accLine3Bal = rootView.findViewById(R.id.tv_accLine3Bal);
        ConstraintLayout tv_accLine3Wrapper = rootView.findViewById(R.id.tv_accLine3Wrapper);

        UserModel user = Singleton.getInstance().user;
        ArrayList<AccountInfo> accounts = user.getAccounts();

        DecimalFormat dFormat = new DecimalFormat("#,###.00");

        if (accounts.size() > 0) {
            AccountInfo acc = accounts.get(0);
            tv_accLine1.setText(acc.toString());
            tv_accLine1Bal.setText("CAD " + dFormat.format(acc.getAccountBalance()));
            tv_accLine1Wrapper.setVisibility(View.VISIBLE);
        } else {
            tv_accLine1Wrapper.setVisibility(View.GONE);
        }

        if (accounts.size() > 1) {
            AccountInfo acc = accounts.get(1);
            tv_accLine2.setText(acc.toString());
            tv_accLine2Bal.setText("CAD " + dFormat.format(acc.getAccountBalance()));
            tv_accLine2Wrapper.setVisibility(View.VISIBLE);
        } else {
            tv_accLine2Wrapper.setVisibility(View.GONE);
        }

        if (accounts.size() > 2) {
            AccountInfo acc = accounts.get(2);
            tv_accLine3.setText(acc.toString());
            tv_accLine3Bal.setText("CAD " + dFormat.format(acc.getAccountBalance()));
            tv_accLine3Wrapper.setVisibility(View.VISIBLE);
        } else {
            tv_accLine3Wrapper.setVisibility(View.GONE);
        }
    }

    private void setupCards(View rootView){
        TextView tv_cardLine1 = rootView.findViewById(R.id.tv_cardLine1);
        TextView tv_cardLine1Bal = rootView.findViewById(R.id.tv_cardLine1Bal);
        ProgressBar progressBar_visa = rootView.findViewById(R.id.progressBar_visa);
        TextView tv_creditLimit = rootView.findViewById(R.id.tv_creditLimit);

        ConstraintLayout tv_cardLine1Wrapper1 = rootView.findViewById(R.id.tv_cardLine1Wrapper1);
        LinearLayout tv_cardLine1Wrapper2 = rootView.findViewById(R.id.tv_cardLine1Wrapper2);
        ConstraintLayout tv_cardLine1Wrapper3 = rootView.findViewById(R.id.tv_cardLine1Wrapper3);
        ConstraintLayout tv_cardLine1Wrapper4 = rootView.findViewById(R.id.tv_cardLine1Wrapper4);

        UserModel user = Singleton.getInstance().user;
        List<CardInfoModel> cards = user.getCards().stream().filter(e -> e.getCardType() == 1).collect(Collectors.toList());

        DecimalFormat dFormat = new DecimalFormat("#,###.00");

        if (cards.size() > 0) {
            CardInfoModel acc = cards.get(0);
            tv_cardLine1.setText("VISA (" + acc.getCardNumber().substring(acc.getCardNumber().length()-4) + ")");
            tv_cardLine1Bal.setText("CAD " + dFormat.format(acc.getAccountBalance()));
            tv_creditLimit.setText("Credit Limit: " + dFormat.format(acc.getMaxLimit()));
            progressBar_visa.setMax((int)acc.getMaxLimit());
            progressBar_visa.setProgress((int)acc.getAccountBalance());

            tv_cardLine1Wrapper1.setVisibility(View.VISIBLE);
            tv_cardLine1Wrapper2.setVisibility(View.VISIBLE);
            tv_cardLine1Wrapper3.setVisibility(View.VISIBLE);
            tv_cardLine1Wrapper4.setVisibility(View.VISIBLE);
        } else {
            tv_cardLine1Wrapper1.setVisibility(View.GONE);
            tv_cardLine1Wrapper2.setVisibility(View.GONE);
            tv_cardLine1Wrapper3.setVisibility(View.GONE);
            tv_cardLine1Wrapper4.setVisibility(View.GONE);
        }
    }

    private void manageSplitWithFriends(View rootView) {
        TextView editTextNumberDecimal = rootView.findViewById(R.id.editTextNumberDecimal);
        editTextNumberDecimal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter()});
        Button btn_split = rootView.findViewById(R.id.btn_split);

        btn_split.setOnClickListener(v -> {
            String amount = editTextNumberDecimal.getText().toString();
            Intent splitBillIntent = new Intent(HomeFragment.this.getActivity(), SplitBillActivity.class);
            if (!amount.isEmpty()){
                splitBillIntent.putExtra("amount", amount);
            }

            startActivity(splitBillIntent);
        });
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter() {
            mPattern=Pattern.compile("[0-9]{0,6}(\\.[0-9]{0,2})?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            CharSequence match = TextUtils.concat(dest.subSequence(0, dstart), source.subSequence(start, end), dest.subSequence(dend, dest.length()));
            Matcher matcher=mPattern.matcher(match);
            if(!matcher.matches())
                return "";
            return null;
        }

    }
}