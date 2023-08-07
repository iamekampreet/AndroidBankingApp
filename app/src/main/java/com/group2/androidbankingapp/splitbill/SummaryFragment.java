package com.group2.androidbankingapp.splitbill;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.snackbar.Snackbar;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.SingleMessageResponseModel;
import com.group2.androidbankingapp.api.SplitBillService;
import com.group2.androidbankingapp.utils.Singleton;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        SplitBillActivity splitBillActivity = ((SplitBillActivity) getActivity());
        splitBillActivity.setAppBarHeight(getResources().getDimensionPixelSize(R.dimen.app_bar_summary_height));
        splitBillActivity.setSummaryAppBarVisibility(View.VISIBLE);

        double totalRequestAmount = mSplitInfoDetailModel.friendInfos.stream()
                .map(contact -> contact.getAmount())
                .reduce(0.0, (subtotal, element) -> subtotal + element);
        splitBillActivity.updateTotalRequestAmount(totalRequestAmount);

        RecyclerView summaryRecyclerView = view.findViewById(R.id.recyclerView_summary);
        SelectedContactWithAmountAdapter adapter = new SelectedContactWithAmountAdapter(mSplitInfoDetailModel.getFriendInfos());
        summaryRecyclerView.setAdapter(adapter);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TextView despositAccountTextView = view.findViewById(R.id.textView_deposit_account);
        TextView yourEmailTextView = view.findViewById(R.id.textView_email_value);
        Button sendSplitRequestButton = view.findViewById(R.id.button_send);
        Button editButton = view.findViewById(R.id.button_edit);
        ProgressBar apiProgressBar = view.findViewById(R.id.progressBar_api);

        despositAccountTextView.setText(mSplitInfoDetailModel.getDepositInfo().toString());
        yourEmailTextView.setText(mSplitInfoDetailModel.getUserEmail());

        sendSplitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiProgressBar.setVisibility(View.VISIBLE);

                SplitBillService splitBillService = Singleton.getRetrofitInstance().create(SplitBillService.class);
                Call<SingleMessageResponseModel> response = splitBillService.requestSplitBill(mSplitInfoDetailModel);

                response.enqueue(new Callback<SingleMessageResponseModel>() {
                    @Override
                    public void onResponse(Call<SingleMessageResponseModel> call, Response<SingleMessageResponseModel> response) {
                        Log.d("SummaryFrament", response.message());
                        apiProgressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            SplitBillActivity splitBillActivity = ((SplitBillActivity) getActivity());
                            splitBillActivity.setAppBarHeight(getResources().getDimensionPixelSize(R.dimen.app_bar_height_full));
                            splitBillActivity.setSummaryAppBarVisibility(View.GONE);
                            SummaryFragment.this.getActivity().getSupportFragmentManager()
                                    .popBackStack(SplitBillActivity.ROOT, POP_BACK_STACK_INCLUSIVE);

                            Toast.makeText(getContext(),
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(),
                                    response.message(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleMessageResponseModel> call, Throwable t) {
                        apiProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }
}