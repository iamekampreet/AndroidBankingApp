package com.group2.androidbankingapp.splitbill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.androidbankingapp.R;

import org.parceler.Parcels;

public class SummaryFragment extends Fragment {
    private static final String ARG_PARAM_SUMMARY_MODEL = "SUMMARY_MODEL";

    private SplitInfoDetailModel mSplitInfoDetailModel;

    public SummaryFragment() {
        // Required empty public constructor
    }

    public static SummaryFragment newInstance(SplitInfoDetailModel splitInfoDetailModel) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_SUMMARY_MODEL, Parcels.wrap(splitInfoDetailModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSplitInfoDetailModel = (SplitInfoDetailModel) Parcels.unwrap(getArguments().getParcelable (ARG_PARAM_SUMMARY_MODEL));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("SummaryFragment", mSplitInfoDetailModel.getUserEmail());
    }
}