package com.example.myapplication;



import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public ArrayList<DataList> arrayList = new ArrayList<DataList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView list = findViewById(R.id.list);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());


        executor.execute(new Runnable() {
            JSONParser  JsonParser = new JSONParser();
            JSONObject jsonObject;
            @Override
            public void run() {

                InputStreamReader responseBodyReader = null;
                try {
                    URL  Endpoint = new URL("https://reqres.in/api/users");
                    HttpsURLConnection myConnection =(HttpsURLConnection) Endpoint.openConnection();
                    if (myConnection.getResponseCode() == 200) {
                        InputStream responseBody = myConnection.getInputStream();
                        responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");
                        jsonObject =(JSONObject) JsonParser.parse(responseBodyReader);
                    } else {
                        // Error handling code goes here
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        try {

                            JSONArray ja_data = (JSONArray) jsonObject.get("data");
                            for (int i = 0 ; i < ja_data.size();i++)
                            {
                                JSONObject jObj = (JSONObject) ja_data.get(i);
                                String email = jObj.get("email").toString();
                                String first_name = jObj.get("first_name").toString();
                                String last_name = jObj.get("last_name").toString();
                                String image =jObj.get("avatar").toString();
                                String full_name = first_name + " "+ last_name;
                                arrayList.add(new DataList(full_name,email,image));
                            }

                            CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
                            list.setAdapter(customAdapter);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

}
