package com.example.inclass07;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by akki0923 on 2/19/18.
 */

public class AsyncGetData  extends AsyncTask<String, Void, ArrayList<Article>> {
    IData iData;

    public AsyncGetData(IData iData) {
        this.iData = iData;
    }


    @Override
    protected void onPreExecute() {

    }

    protected ArrayList<Article> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        ArrayList<Article> results = new ArrayList<>();
        try {

            URL url = new URL( strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONArray articles = root.getJSONArray("articles");
                //  Log.d("test", String.valueOf(articles.length()));
                for(int i = 0; i < articles.length(); i++){
                    JSONObject articleJson = articles.getJSONObject(i);
                    JSONObject sourceJson = articleJson.getJSONObject("source");
                    Article article = new Article();
                    // Source source = new Source();
                    // article.author = articleJson.getString("author");
                    article.description = articleJson.getString("description");
                    article.publishedat = articleJson.getString("publishedAt");
                    article.title = articleJson.getString("title");
                    //    article.url = articleJson.getString("url");
                    article.urltoimage = articleJson.getString("urlToImage");

                    results.add(article);
                }
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            //Close open connections and reader
            if (connection != null) {
                connection.disconnect();
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        iData.handleData(articles);
    }

    public interface IData{
        public  void handleData(ArrayList<Article> articles);
    }
}


