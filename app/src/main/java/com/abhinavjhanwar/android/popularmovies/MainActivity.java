package com.abhinavjhanwar.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            //Check for network connection
            NetworkCheck networkCheck = new NetworkCheck();
            networkCheck.execute();
        }
    }

    //Network check, thanks to http://stackoverflow.com/a/6493572
    public class NetworkCheck extends AsyncTask<Void, Void, Void> {

        public boolean hasActiveInternetConnection() {

            String LOG_TAG = "Popular Movies Network";

            if (isNetworkAvailable()) {
                try {
                    //Get response code from google to check if there is uplink
                    HttpURLConnection urlConnection = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                    urlConnection.setConnectTimeout(1500);
                    urlConnection.connect();
                    return (urlConnection.getResponseCode() == 204 && urlConnection.getContentLength() == 0);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error checking internet connection", e);
                }
            } else {
                Log.d(LOG_TAG, "No network available!");
            }
            return false;
        }

        private boolean isNetworkAvailable() {
            //Check if WiFi or data option is enabled
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(hasActiveInternetConnection()) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new com.abhinavjhanwar.android.popularmovies.fragments.MovieFragment())
                        .commitAllowingStateLoss();
            } else {
                //Show error when internet is not connected
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new com.abhinavjhanwar.android.popularmovies.fragments.NoNetworkFragment())
                        .commitAllowingStateLoss();
            }
            return null;
        }
    }
}
