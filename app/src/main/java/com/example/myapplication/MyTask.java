package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MyTask extends AsyncTask<String,String,String> {

    public ArrayList<DataList> arrayList = new ArrayList<DataList>();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog to show the user what is happening


    }
        @Override
        protected String doInBackground(String... params) {
            InputStreamReader responseBodyReader = null;
            try {
                URL  Endpoint = new URL("https://reqres.in/api/users");
                HttpsURLConnection myConnection =(HttpsURLConnection) Endpoint.openConnection();
                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                     responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                    return  responseBodyReader.toString();
                } else {
                    // Error handling code goes here
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
           catch (IOException e) {
                e.printStackTrace();
            }
            return responseBodyReader.toString();

            //////

        }

        @Override
        protected void onPostExecute(String s) {
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray ja_data = jsonObj.getJSONArray("data");
                    for (int i = 0 ; i < ja_data.length();i++)
                    {
                        JSONObject jObj = ja_data.getJSONObject(i);
                        String email = jObj.getString("email");
                        String first_name = jObj.getString("first_name");
                        String last_name = jObj.getString("last_name");
                        String image =jObj.getString("avatar");
                        String full_name = first_name + " "+ last_name;
                        arrayList.add(new DataList(full_name,email,image));
                    }
                }
                catch (Exception e)
                {

                }



            // show results
        }
}
