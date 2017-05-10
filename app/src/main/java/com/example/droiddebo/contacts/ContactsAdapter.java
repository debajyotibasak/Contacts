package com.example.droiddebo.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom ContactsAdapter from ArrayAdapter
public class ContactsAdapter extends ArrayAdapter<Contacts> {
    public ContactsAdapter(Context context, ArrayList<Contacts> contactsList) {
        super(context, 0, contactsList);
    }

    //Returns List Item View that displays information about contacts at a given position in the list of contacts
    //Recycling Views
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, parent, false);
        }

        //Find the contacts at a given position
        Contacts currentContacts = getItem(position);

        //Find the TextView and display name in the TextView
        TextView nameView = (TextView) listItemView.findViewById(R.id.name);
        nameView.setText(currentContacts.getName());

        //Find the TextView and display email in the TextView
        TextView emailView = (TextView) listItemView.findViewById(R.id.email);
        emailView.setText(currentContacts.getEmail());

        //Find the TextView and display gender in the TextView
        TextView genderView = (TextView) listItemView.findViewById(R.id.gender);
        genderView.setText(currentContacts.getGender());

        //Find the TextView and display phone number in the TextView
        TextView phoneView = (TextView) listItemView.findViewById(R.id.phone);
        phoneView.setText(currentContacts.getPhone());

        return listItemView;
    }
}
