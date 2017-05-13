package com.example.droiddebo.contacts;


public class Contacts {

    // Name of the Contact
    private String mName;

    // Email ID of the Contact
    private String mEmail;

    // Gender of the Contact
    private String mGender;

    // Phone no. of the Contact;
    private String mPhone;

    // Website URL of the earthquake
    //private String mUrl;

    //public constructor
    public Contacts(String name, String email, String gender, String phone /*String url*/){
        mName = name;
        mEmail = email;
        mGender = gender;
        mPhone = phone;
        //mUrl = url;
    }

    //Returns Name
    public String getName() { return mName; }

    //Returns Email
    public String getEmail() { return mEmail; }

    //Returns gender
    public String getGender() { return mGender; }

    //Returns Phone No.
    public String getPhone() { return mPhone; }

    //Returns URL
    //public String getURL() { return mUrl; }
}
