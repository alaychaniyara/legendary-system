package com.example.rubby.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/*
* InClass 05
* Photo Viewer App
* Group 22
* Alay Chaniyara
   * Adam Burnam
* */
public class MainActivity extends AppCompatActivity implements AsyncGetImages.IData{

    TextView searchKeyword,imageID;
    Button buttonGo;
    ImageView selectedImage;
    Button buttonPrev;
    Button buttonNext;

    String keyword;
    ArrayList<String> imgs;
    int imageIndex;
    Bitmap image;

    AlertDialog.Builder keywordAlert;
    ProgressDialog progressDialog,pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        imageID=findViewById(R.id.textViewImageID);
        searchKeyword = findViewById(R.id.searchKeyword);
        buttonGo = findViewById(R.id.buttonGo);
        selectedImage = findViewById(R.id.selectedImage);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonNext = findViewById(R.id.buttonNext);

        progressDialog = new ProgressDialog(this);
        pDialog = new ProgressDialog(this);

        buttonPrev.setEnabled(false);
        buttonNext.setEnabled(false);

        if(isConnected()) {
            new AsyncGetKeywords().execute("http://dev.theappsdr.com/apis/photos/keywords.php");
        }
        else
        {
            Toast.makeText(MainActivity.this, "NO Internet Please Check Connection", Toast.LENGTH_LONG).show();

        }

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordAlert.show();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageIndex > 0) {
                    imageIndex--;

                    pDialog.setTitle("Loading Photo");

                  pDialog.setCancelable(false);
                  pDialog.show();

                    new AsyncGetImages(MainActivity.this).execute(imgs.get(imageIndex));
                }
                else{

                    pDialog.setTitle("Loading Photo");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    imageIndex = imgs.size() - 1;
                    new AsyncGetImages(MainActivity.this).execute(imgs.get(imageIndex));
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageIndex < imgs.size()-1){
                    imageIndex++;

                    pDialog.setTitle("Loading Photo");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    new AsyncGetImages(MainActivity.this).execute(imgs.get(imageIndex));
                }
                else{

                    pDialog.setTitle("Loading Photo");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    imageIndex = 0;
                    new AsyncGetImages(MainActivity.this).execute(imgs.get(imageIndex));
                }
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()
                ||(networkInfo.getType()!= connectivityManager.TYPE_WIFI&&
                networkInfo.getType()!=connectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handleData(Bitmap bitmap) {
        this.image=bitmap;
        progressDialog.hide();

        pDialog.hide();
        selectedImage.setImageBitmap(this.image);
        imageID.setText("Image "+(imageIndex+1) +" Of "+imgs.size());
    }

    public class AsyncGetKeywords extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String results = null;
            try {

                URL url = new URL( strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    results = stringBuilder.toString();
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
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null) {
                final String[] kw = s.split(";");
                keywordAlert = new AlertDialog.Builder(MainActivity.this);
                keywordAlert.setCancelable(false);
                keywordAlert.setTitle("Choose a Keyword").setItems(kw, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchKeyword.setText(kw[which]);
                        keyword = kw[which];
                        progressDialog.setTitle("Loading Dictionary");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        for (int i = 0; i < 1000; i++) {
                            for (int j=0;j<1000000;j++){}
                        }
                            new AsyncGetImageList().execute("http://dev.theappsdr.com/apis/photos/index.php?keyword=" + keyword);
                    }
                });
                Log.d("test", s);
            }
        }
    }

    public class AsyncGetImageList extends AsyncTask<String, Void, ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<String> results = new ArrayList();
            try {
                URL url = new URL( strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        results.add(line);
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
            }
            finally {
                //Close open connections and reader
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return results;
        }

       @Override
        protected void onPostExecute(ArrayList<String> s) {

   //        Log.d("demo",s.get(0));
            if(s.size() > 0) {
                imgs = s;
                imageIndex = 0;
                buttonPrev.setEnabled(true);
                buttonNext.setEnabled(true);

         //       pDialog.setTitle("Loading Photo");
           //     pDialog.show();
                new AsyncGetImages(MainActivity.this).execute(imgs.get(0));
            }
            else{
              //  pDialog.hide();
               progressDialog.hide();
                buttonPrev.setEnabled(false);
                buttonNext.setEnabled(false);
                Toast.makeText(MainActivity.this, "No Images Found", Toast.LENGTH_SHORT).show();

                imageID.setText("");
                selectedImage.setImageDrawable(getDrawable(R.drawable.tranparent));
            }
        }
    }

}
