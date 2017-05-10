package com.example.droiddebo.contacts;

/**
 * Created by DROID DEBO on 11-05-2017.
 */

public class Contacts {

    // Name of the Contact
    private String mName;

    // Email ID of the Contact
    private String mEmail;

    // Gender of the Contact
    private String mGender;

    // Phone no. of the Contact;
    private String mPhone;

    //public constructor
    public Contacts(String name, String email, String gender, String phone){
        mName = name;
        mEmail = email;
        mGender = gender;
        mPhone = phone;
    }

    //Returns Name
    public String getName() { return mName; }

    //Returns Email
    public String getEmail() { return mEmail; }

    //Returns gender
    public String getGender() { return mGender; }

    //Returns Phone No.
    public String getPhone() { return mPhone; }
}
