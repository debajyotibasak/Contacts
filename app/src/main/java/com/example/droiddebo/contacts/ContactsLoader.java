package com.example.droiddebo.contacts;

import android.app.Activity;
import android.content.AsyncTaskLoader;

import com.example.droiddebo.contacts.DbUtils.DbHelper;

import java.util.List;

public class ContactsLoader extends AsyncTaskLoader<List<Contacts>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = ContactsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;
    private Activity mActivity;


    public ContactsLoader(Activity activity, String url) {
        super(activity);
        mUrl = url;
        mActivity = activity;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Contacts> loadInBackground() {
        if (mUrl == null) {
            return null;

        }
        // Perform the network request, parse the response, and extract a list of contacts.
        return QueryUtils.fetchContactsData(mUrl, new ContactDataSource(mActivity, new DbHelper(mActivity)));
    }
}
