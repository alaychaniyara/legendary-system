package com.example.rubby.inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rubby on 2/12/2018.
 */

public class AsyncGetImages extends AsyncTask<String, Void, Void> {

    IData iData;
    Bitmap image = null;

    public AsyncGetImages(IData iData){
        this.iData = iData;
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection connection = null;
       try {
            URL url = new URL( strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                image = BitmapFactory.decodeStream(connection.getInputStream());
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            //Close open connections and reader
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(image != null){
            iData.handleData(image);
        }
    }

    public static interface IData{
        public void handleData(Bitmap bitmap);
    }
}
