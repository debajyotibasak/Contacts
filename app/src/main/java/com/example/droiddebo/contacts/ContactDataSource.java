package com.example.droiddebo.contacts;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.droiddebo.contacts.DbUtils.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactDataSource {

    public static final String LOG_TAG = ContactDataSource.class.getName();
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Activity mActivity;

    public ContactDataSource(Activity activity, DbHelper dbHelper){
        mActivity = activity;
        mDbHelper = dbHelper;
    }

    void open() throws SQLException{
        mDatabase = mDbHelper.getWritableDatabase();
    }

    void close(){
        mDbHelper.close();
    }

    void createContact(Contacts contact) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mActivity.getString(R.string.table_column_name),contact.getName());
        contentValues.put(mActivity.getString(R.string.table_column_email),contact.getEmail());
        contentValues.put(mActivity.getString(R.string.table_column_gender),contact.getGender());
        contentValues.put(mActivity.getString(R.string.table_column_phone),contact.getPhone());
        if (mDatabase != null) {
            final long insertId = mDatabase.insert(
                    mActivity.getString(R.string.table_name), null, contentValues);
            Log.i(LOG_TAG,"insert ID:\t" + insertId);
            Cursor cursor = mDatabase.query(
                    mActivity.getString(R.string.table_name),
                    mActivity.getResources().getStringArray(R.array.table_columns),
                    mActivity.getString(R.string.table_column_id) + " = " + insertId,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                cursor.close();
            } else {
                Log.e(this.getClass().getName(),"NULL CURSOR");
            }
        }
        else {
            Log.e(this.getClass().getName(),"Database:\t" + mDatabase);
        }
    }

    List<Contacts> getAllContacts() {

        if (mDatabase != null) {
            List<Contacts> contacts = new ArrayList<>();
            Cursor cursor = mDatabase.query(
                    mActivity.getString(R.string.table_name),
                    mActivity.getResources().getStringArray(R.array.table_columns),
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Log.i(LOG_TAG,"CONTACT_ID:\t" + cursor.getInt(0));
                    Log.i(LOG_TAG,"CONTACT_NAME:\t" + cursor.getString(1));
                    Log.i(LOG_TAG,"CONTACT_EMAIL:\t" + cursor.getString(2));
                    Log.i(LOG_TAG,"CONTACT_GENDER:\t" + cursor.getString(3));
                    Log.i(LOG_TAG,"CONTACT_PHONE:\t" + cursor.getString(4));
                    contacts.add(new Contacts(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                    cursor.moveToNext();
                }
                cursor.close();
            }
            return contacts;
        } else {
            Log.e(this.getClass().getName(),"Database NULL:\t" + mDatabase);
            return null;
        }
    }

    boolean isTableEmpty() {

        if (mActivity != null && mDatabase != null && mDbHelper != null) {
            String count = "SELECT count(*) FROM " + mActivity.getString(R.string.table_name);
            Cursor cursor = mDatabase.rawQuery(count,null);
            if (cursor != null) {
                cursor.moveToFirst();
                int intCount = cursor.getCount();
                Log.i(this.getClass().getName(), "No. of counts:\t" + intCount);
                cursor.close();
                return intCount <= 0;
            } else {
                Log.e(this.getClass().getName(),"Cursor NUll");
                return false;
            }
        }
        Log.e(this.getClass().getName(),"NULL OBJ");
        return false;
    }
}
