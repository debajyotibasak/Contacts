package com.example.droiddebo.contacts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_activity);

        // Fake Contacts
        ArrayList<Contacts> contacts = new ArrayList<>();
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));
        contacts.add(new Contacts("Ravi Tamada", "ravi@gmail.com", "Male", "+91 0000"));

        //Create a New ArrayAdapter of Contacts
        ContactsAdapter adapter = new ContactsAdapter(this, contacts);

        //Set the Adapter so that the list can be populated in the UI
        //Linking ListView in Layout
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
