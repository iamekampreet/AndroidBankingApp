package com.group2.androidbankingapp.splitbill;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactHelper {

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


    public ArrayList<ContactRowModel> readContacts(ContentResolver contentResolver) {
        ArrayList<ContactModel> contacts = new ArrayList<>();

        Cursor cursor = contentResolver.query(
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

                Cursor cursor_details = contentResolver.query(
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
