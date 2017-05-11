package com.example.droiddebo.contacts;

import android.content.AsyncTaskLoader;
import android.content.Context;

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

    public ContactsLoader(Context context, String url) {
        super(context);
        mUrl = url;
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
        List<Contacts> ListContacts = QueryUtils.fetchContactsData(mUrl);
        return ListContacts;
    }
}
