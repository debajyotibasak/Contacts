package com.example.droiddebo.contacts;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// Helper Methods to receive requests from JSON
public class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    public QueryUtils() {

    }

    public static List<Contacts> fetchContactsData(String requestURL, ContactDataSource contactDataSource) {
        //Create URL object
        URL url = createUrl(requestURL);

        //Perform HTTP Request to the URL and receive a JSON Response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of contacts
        // run extractFeatureFromJson(jsonResponse)
        contactDataSource.open();
        storeToDb(extractFeatureFromJson(jsonResponse), contactDataSource);
        while (contactDataSource.isTableEmpty()) {
            Log.w(LOG_TAG,"EMPTY TABLE");
        }
        List<Contacts> contacts = contactDataSource.getAllContacts();
        contactDataSource.close();
        if (contacts != null) {
            return contacts;
        }
        else {
            return null;
        }
    }

    // Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    // Make an HTTP request to the given URL and return a String as the response
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode()==200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line!=null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Contacts> extractFeatureFromJson(String contactsJSON) {
        // If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(contactsJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding contacts to
        List<Contacts> contactList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(contactsJSON);

            // Extract the JSONArray associated with the key called "contacts", which represents a list of contacts
            JSONArray contactsArray = baseJsonResponse.getJSONArray("contacts");

            // For each contact in the contactsArray, create an contact object
            for(int i=0; i < contactsArray.length(); i++){

                // Get a single contact at position i within the list of contacts
                JSONObject currentContact = contactsArray.getJSONObject(i);

                // Extract the value for the key called "name"
                String name = currentContact.getString("name");

                // Extract the value for the key called "email"
                String email = currentContact.getString("email");

                // Extract the value for the key called "gender"
                String gender = currentContact.getString("gender");

                //Get contact object and extract phone number as mobile number
                JSONObject call = currentContact.getJSONObject("phone");

                String phone = call.getString("mobile");

                Contacts contact = new Contacts(name, email, gender, phone);

                contactList.add(contact );
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
    return contactList;
    }

    private static void storeToDb(List<Contacts> contacts, ContactDataSource contactDataSource) {
        for (Contacts contact: contacts) {
            contactDataSource.createContact(contact);
        }
    }
}
