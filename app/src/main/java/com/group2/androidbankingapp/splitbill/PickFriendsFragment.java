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
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    ContactsContract.Contacts.DISPLAY_NAME
            };

    private static final String[] PROJECTION_DETAIL =
            {
                    ContactsContract.Data.DATA1,
                    ContactsContract.CommonDataKinds.Email._ID,
                    ContactsContract.Data.MIMETYPE
            };

    private static final String SELECTION = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    private String searchString;
    private String[] selectionArgs = { searchString };

    private static final String SELECTION_DETAILS =
            ContactsContract.Data.CONTACT_ID + " = ?"
                    + " AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";

    List<Integer> selectedContacts = new ArrayList<>();

    LinearLayout selectedContactContainer;
    HorizontalScrollView horizontalScrollView;
    TextView titleSelectedTextView;
    ContactsAdapter contactsAdapter;
    RecyclerView contactRecyclerView;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean result) {
                            readContactsAsync();
                        }
                    });

    ArrayList<ContactRowModel> contacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pick_friends, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            readContactsAsync();
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
                return readContacts();
            }

            @Override
            protected void onPostExecute(List<ContactRowModel> contactRowModels) {
                super.onPostExecute(contactRowModels);
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

        contacts = readContacts();
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
        } else {
            horizontalScrollView.setVisibility(View.GONE);
            titleSelectedTextView.setVisibility(View.GONE);
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

    private ArrayList<ContactRowModel> readContacts() {
        ArrayList<ContactModel> contacts = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        int size = cursor.getCount();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactModel contactModel = new ContactModel();

                String contactId = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.d("contacts", "contactId = " + contactId + " Contactkey = " + contactName);
                contactModel.setName(contactName);

                Cursor cursor_details = getActivity().getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        PROJECTION_DETAIL,
                        SELECTION_DETAILS,
                        new String[]{ contactId },
                        null
                );

                if (cursor_details != null
                        && cursor_details.moveToNext()) {
                    String phoneNumber = cursor_details.getString(
                            cursor_details.getColumnIndex(ContactsContract.Data.DATA1));
                    Log.d("contacts", "Phone number = " + phoneNumber);
                    contactModel.setPhoneNumber(phoneNumber);

                    cursor_details.close();
                }
                contacts.add(contactModel);
            }
            cursor.close();
        }

        Set<String> initialChars = contacts.stream()
                .map(contactModel -> contactModel.getName().substring(0, 1))
                .collect(Collectors.toSet());

        ArrayList<ContactRowModel> rowModels = new ArrayList<>();
        initialChars.stream()
                .sorted()
                .forEach(iChar -> {
                    List<ContactRowModel> tempModels = contacts.stream()
                            .filter(contact -> contact.getName().substring(0, 1).equals(iChar))
                            .sorted()
                            .map(contact -> new ContactRowModel(false, null, contact))
                            .collect(Collectors.toList());
                    rowModels.add(new ContactRowModel(true, iChar, null));
                    rowModels.addAll(tempModels);
                });

        return rowModels;
    }
}