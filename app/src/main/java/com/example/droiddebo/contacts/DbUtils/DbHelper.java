package com.example.droiddebo.contacts.DbUtils;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.droiddebo.contacts.R;

public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getName();

    public Activity mActivity;

    public DbHelper(Activity activity){
        super(
                activity,
                activity.getString(R.string.database_name),
                null,
                activity.getResources().getInteger(R.integer.database_version));
        mActivity = activity;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String dropTable = "DROP TABLE IF EXISTS contacts;";
        db.execSQL(dropTable);

        final String tableCreateQuery = "create table " +
                mActivity.getString(R.string.table_name) + "(" +
                mActivity.getString(R.string.table_column_id) + " integer primary key autoincrement, " +
                mActivity.getString(R.string.table_column_name) + " text not null, " +
                mActivity.getString(R.string.table_column_email) + " text, " +
                mActivity.getString(R.string.table_column_gender) + " text not null, " +
                mActivity.getString(R.string.table_column_phone) + " text);";

        Log.i(LOG_TAG,"CREATE QUERY:\n" + tableCreateQuery);
        db.execSQL(tableCreateQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LOG_TAG,"Upgrading database from version " + oldVersion + "to version " + newVersion);
        onCreate(db);
    }
}
