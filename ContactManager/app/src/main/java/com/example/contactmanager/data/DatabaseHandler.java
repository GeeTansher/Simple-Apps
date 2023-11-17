package com.example.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.util.utilClass;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, utilClass.DATABASE_NAME, null, utilClass.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + utilClass.TABLE_NAME + "("
                + utilClass.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                utilClass.KEY_NAME + " TEXT NOT NULL," +
                utilClass.KEY_PHONE_NUMBER + " TEXT NOT NULL" + ")";

        db.execSQL(CREATE_CONTACT_TABLE);  // creating table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.dropTable);
        db.execSQL(DROP_TABLE, new String[]{utilClass.DATABASE_NAME});

        // creating table again
        onCreate(db);
    }

    // CRUD operations - Create, Read, Update, Delete

    // add
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(utilClass.KEY_NAME, contact.getName());
        values.put(utilClass.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // insert row
        db.insert(utilClass.TABLE_NAME, null, values);
        db.close();
    }

    // get 1 contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Contact Contact;
        try (Cursor cursor = db.query(utilClass.TABLE_NAME,
                new String[]{utilClass.KEY_ID, utilClass.KEY_NAME, utilClass.KEY_PHONE_NUMBER},
                utilClass.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null)) {

            if (cursor != null) {
                cursor.moveToFirst();
            }
            Contact = new Contact();
            assert cursor != null;
            Contact.setId((Integer.parseInt(cursor.getString(0))));
            Contact.setName(cursor.getString(1));
            Contact.setPhoneNumber(cursor.getString(2));
        }

        return Contact;
    }

    // get all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // select all contacts
        String selectAll = "SELECT * FROM " + utilClass.TABLE_NAME;
        try (Cursor cursor = db.rawQuery(selectAll, null)) {

            // loop through data
            if (cursor.moveToFirst()) {
                do {
                    Contact contact = new Contact();
                    contact.setId((Integer.parseInt(cursor.getString(0))));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));

                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        }

        return contactList;
    }

    // update
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(utilClass.KEY_NAME, contact.getName());
        cv.put(utilClass.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        // update
        // will return the id of which it updates the values
        return db.update(utilClass.TABLE_NAME, cv, utilClass.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

    }

    // delete
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(utilClass.TABLE_NAME, utilClass.KEY_ID + "=?",
                new String[]{String.valueOf(contact.getId())});

    }

    // get contacts count
    public int getCount() {
        String countQuery = "SELECT * FROM " + utilClass.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery(countQuery, null)) {

            return cursor.getCount();
        }
    }
}
