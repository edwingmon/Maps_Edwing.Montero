package com.example.mapsedwing.Threads;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiThread extends AsyncTask<Void, Void, String> {

    private double latitude;
    private double longitude;

    public ApiThread(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String data = null;
        try {
            //Make API connection
            URL url = new URL("https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude);
            Log.i("logtest", "https://api.sunrise-sunset.org/json?lat=" + latitude + "&lng=" + longitude);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            data = bufferedReader.readLine();
            Log.i("logtest", data);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String data) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(data);

            jsonObject = jsonObject.getJSONObject("results");

            String sunrise = jsonObject.getString("sunrise");
            Log.i("logtest", "------>" + sunrise);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
