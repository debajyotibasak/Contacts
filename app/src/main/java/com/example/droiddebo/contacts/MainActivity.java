package com.example.droiddebo.contacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fake Contacts
        ArrayList<String> contacts = new ArrayList<>();
        contacts.add("Arijit Banerjee");
        contacts.add("Biplav Mondal");
        contacts.add("Tanmay Mondal");
        contacts.add("Chandan Das");
        contacts.add("Rahul Phugal");
        contacts.add("Ankit Muffins");
        contacts.add("Avik Mondal");
        contacts.add("Abhirup Das");
        contacts.add("Anindya Dawn");

        //Linking ListView in Layout
        ListView contactListView = (ListView) findViewById(R.id.list);

        //Create a New ArrayAdapter of Contacts
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);

        //Set the Adapter so that the list can be populated in the UI
        contactListView.setAdapter(adapter);
    }
}
