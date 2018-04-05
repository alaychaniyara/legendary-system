package com.example.apps_itunes;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by akki0923 on 2/19/18.
 */

public class AsyncGetData extends AsyncTask<String, Void, ArrayList<App>> {
    IData iData;
    ArrayList uniquegenre=new ArrayList();

    public AsyncGetData(IData iData) {
        this.iData = iData;
    }


    @Override
    protected void onPreExecute() {

    }

    protected ArrayList<App> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        ArrayList<App> results = new ArrayList<>();
        try {

            URL url = new URL( strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                JSONObject root = new JSONObject(json);
                JSONObject feed=root.getJSONObject("feed");
                JSONArray articles = feed.getJSONArray("results");
                uniquegenre.add("All");

                //  Log.d("test", String.valueOf(articles.length()));
                for(int i = 0; i < articles.length(); i++){
                    JSONObject articleJson = articles.getJSONObject(i);

                    JSONArray genre = articleJson.getJSONArray("genres");

                    App applist = new App();
                    // article.author = articleJson.getString("author");


                    applist.appName=articleJson.getString("name");
                    applist.artistName=articleJson.getString("artistName");
                    applist.releaseDate=articleJson.getString("releaseDate");
                    applist.imageUrl=articleJson.getString("artworkUrl100");

                    applist.copyRight=articleJson.getString("copyright");

                    for (int j=0;j<genre.length();j++)
                    {


                        JSONObject genreJson = genre.getJSONObject(j);
                        applist.genre.add(genreJson.getString("name"));
                      /*  for(int z=0;z<uniquegenre.size();z++)
                        {
                            if(!genreJson.getString("name").equals(uniquegenre.get(j)))
                            {
                                uniquegenre.add(genre.getString(j));
                            }
                        }
                */
                    }
                  //  JSONObject sourceJson = articleJson.getJSONObject("genres");
                    //applist.genre=sourceJson.getString("name");


                    results.add(applist);
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
    protected void onPostExecute(ArrayList<App> articles) {
        iData.handleData(articles);

    }

    public interface IData{
        public  void handleData(ArrayList<App> articles);
    }
}


