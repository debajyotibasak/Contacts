package com.example.droiddebo.contacts;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Contacts>>{

    //URL for fetching contacts data
    private static final String REQUEST_URL = "http://api.androidhive.info/contacts/";

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /**
     * Constant value for the contacts loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int CONTACTS_LOADER_ID = 1;

    //Adapter for the list of Contacts
    private ContactsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_activity);

        //Find the reference to the list in the layout
        ListView contactListView = (ListView) findViewById(R.id.list);

        //Create an Adapter that takes an Empty List of Contacts
        mAdapter = new ContactsAdapter(this, new ArrayList<Contacts>());

        // Set the adapter so that the list can be populated in the user interface
        contactListView.setAdapter(mAdapter);

        //Start the Async Task to fetch contact data
        /*ContactsAsyncTask task = new ContactsAsyncTask();
          task.execute(REQUEST_URL);*/

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(CONTACTS_LOADER_ID, null, this);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        contactListView.setEmptyView(mEmptyStateTextView);
    }

    @Override
    public Loader<List<Contacts>> onCreateLoader(int id, Bundle bundle) {
        // Create a new loader for the given URL
        return new ContactsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Contacts>> loader, List<Contacts> contacts) {
        // Clear the adapter of previous contacts data
        mAdapter.clear();

        // If there is a valid list of Contacts, then add them to the adapter's data set. This will trigger the ListView to update.
        if (contacts != null && !contacts.isEmpty()) {
            mAdapter.addAll(contacts);
        }

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_contacts);
    }

    @Override
    public void onLoaderReset(Loader<List<Contacts>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /*private class ContactsAsyncTask extends AsyncTask<String, Void, List<Contacts>> {

        @Override
        protected List<Contacts> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Contacts> result = QueryUtils.fetchContactsData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Contacts> data) {
            // Clear the adapter of previous contacts data
            mAdapter.clear();

            // If there is a valid list of Contacts then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }*/
}

