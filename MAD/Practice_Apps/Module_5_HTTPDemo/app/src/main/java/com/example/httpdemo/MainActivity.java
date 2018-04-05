package com.example.httpdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                    Toast.makeText(MainActivity.this, "Internet Connection is There", Toast.LENGTH_SHORT).show();
                    new GetImageAsync((ImageView)findViewById(R.id.imageView)).execute("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/2000px-Android_robot.svg.png");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isConnected (){

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo ==null || !networkInfo.isConnected()||
                (networkInfo.getType()!=ConnectivityManager.TYPE_WIFI&&
                networkInfo.getType()!=ConnectivityManager.TYPE_MOBILE)){

            return false;
        }
        return true;


    }

    private class  GetImageAsync extends AsyncTask<String, Void, Void> {
ImageView imageView;
Bitmap bitmap=null;
        public GetImageAsync(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(bitmap!=null&&imageView!=null){
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            StringBuilder stringBuilder=new StringBuilder();
            HttpURLConnection connection=null;
            bitmap=null;
            try {
                URL url=new URL(params[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    bitmap= BitmapFactory.decodeStream(connection.getInputStream());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (connection!=null){
                    connection.disconnect();
                }
            }

            return null;
        }
    }

}
