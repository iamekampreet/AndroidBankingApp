package com.group2.androidbankingapp.splitbill;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group2.androidbankingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PickFriendsFragment extends Fragment implements OnSelectedContactChangeListener {

    public static final String TAG =  "PickFriendsFragment";

    List<Integer> selectedContacts = new ArrayList<>();

    LinearLayout selectedContactContainer;
    HorizontalScrollView horizontalScrollView;
    TextView titleSelectedTextView;
    ContactsAdapter contactsAdapter;
    RecyclerView contactRecyclerView;
    Button nextButton;

    ContactHelper contactHelper = new ContactHelper();

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean result) {
                            readContactsAsync();
                        }
                    });

    ArrayList<ContactRowModel> contacts;

    public PickFriendsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_friends, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SplitBillActivity) getActivity()).disableExpandableAppBar();
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            if (contacts == null || contacts.isEmpty()) {
                readContactsAsync();
            } else {
                setUpContactList();
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    private void readContactsAsync() {
        //noinspection deprecation
        new AsyncTask<Void, Void, List<ContactRowModel>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected List<ContactRowModel> doInBackground(Void... voids) {
                return contactHelper.readContacts(getContext().getContentResolver());
            }

            @Override
            protected void onPostExecute(List<ContactRowModel> contactRowModels) {
                super.onPostExecute(contactRowModels);
                contacts = (ArrayList<ContactRowModel>) contactRowModels;
                setUpContactList();
            }
        }.execute();
    }

    private void setUpContactList() {
        View view = getView();
        selectedContactContainer = view.findViewById(R.id.container_selected_contacts);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        titleSelectedTextView = view.findViewById(R.id.textView_title_selected);
        ProgressBar contactProgressBar = view.findViewById(R.id.progressBar_contacts);
        TextView loadingContactsTextView = view.findViewById(R.id.textView_loading_contacts);
        nextButton = view.findViewById(R.id.button_pick_friends_next);

//        contacts = readContacts();
        for (ContactRowModel contact:
             contacts) {
            Log.d("contacts", contact.toString());
        }

        contactProgressBar.setVisibility(View.GONE);
        loadingContactsTextView.setVisibility(View.GONE);

        contactRecyclerView = view.findViewById(R.id.recyclerView_contacts);
        contactsAdapter = new ContactsAdapter(contacts);
        contactsAdapter.setOnSelectedContactChangeListener(this);
        contactRecyclerView.setAdapter(contactsAdapter);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ContactModel> selectContactModel = (ArrayList<ContactModel>) selectedContacts.stream()
                        .map(position -> contacts.get(position).data)
                        .collect(Collectors.toList());

                SplitAccountInfoFragment splitAccountInfoFragment =
                        SplitAccountInfoFragment.newInstance(selectContactModel);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_split_bill,
                                splitAccountInfoFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(SplitBillActivity.ROOT)
                        .commit();
            }
        });

        generateSelectedContactList();
    }

    @Override
    public void contactAdded(int position) {
        if (selectedContacts.contains(new Integer(position))) return;

        selectedContacts.add(position);
        generateSelectedContactList();
    }

    @Override
    public void contactRemoved(int position) {
        selectedContacts.remove(new Integer(position));
        generateSelectedContactList();
    }

    private void generateSelectedContactList() {
        if (selectedContacts.size() > 0) {
            horizontalScrollView.setVisibility(View.VISIBLE);
            titleSelectedTextView.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
        } else {
            horizontalScrollView.setVisibility(View.GONE);
            titleSelectedTextView.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }

        selectedContactContainer.removeAllViews();

        selectedContacts.forEach(position -> {
            ContactRowModel selectedContact = contacts.get(position);

            View selectedContactLayout = getLayoutInflater().inflate(R.layout.layout_added_contact, null);
            TextView selectedNameTextView = selectedContactLayout.findViewById(R.id.textView_selected_contact_name);
            TextView initialsTextView = selectedContactLayout.findViewById(R.id.textView_selected_contact_initials);
            ImageView removeContactsImageView = selectedContactLayout.findViewById(R.id.imageView_remove_selected_contact);

            selectedNameTextView.setText(selectedContact.data.getName());
            initialsTextView.setText(selectedContact.data.getName().substring(0, 1).toUpperCase());

            removeContactsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedContacts.remove(new Integer(position));
                    contactsAdapter.updateIsContactSelect(position);
                    selectedContactContainer.removeView(selectedContactLayout);
                    generateSelectedContactList();
                }
            });

            selectedContactContainer.addView(selectedContactLayout);
        });
    }
}