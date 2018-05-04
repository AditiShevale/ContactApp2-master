package com.example.aditi.contactapp2;

import android.net.Uri;
import android.util.Log;

import com.example.aditi.contactapp2.Pojo.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Network {

    final static String BASE_URL="https://api.androidhive.info/json/contacts.json";

    public static List<Contact> fetchContactData(URL url) throws JSONException {
        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Contact> contacts = extractFeatureFromJson(jsonResponse);
        Log.i("adu", String.valueOf(contacts));
        return contacts;

    }


    public static URL buildUrl(){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();
        Log.i("adzi", String.valueOf(builtUri));
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }


    public static List<Contact> extractFeatureFromJson(String contactJson)
            throws JSONException {

        List<Contact> contacts = new ArrayList<>();

        JSONArray ar = new JSONArray(contactJson);

        for (int i = 0; i < ar.length(); i++) {

            JSONObject ob = ar.getJSONObject(i);

            String name = ob.getString("name");
            Log.i("nat",name);

            String phone = ob.getString("phone");
            Log.i("nat",phone);
            String image = ob.getString("image");
            Log.i("nat",image);

            Contact contacts1 = new Contact(name, phone, image);
            contacts.add(contacts1);

        }

        return contacts;
    }



}
