package com.example.apps_itunes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncGetData.IData {
ProgressDialog progressDialog;
ArrayList<App> ListofApps;
TextView genre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genre=findViewById(R.id.textViewGenreDisplay);
        progressDialog = new ProgressDialog(this);
        if (isConnected()) {
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_LONG).show();

            progressDialog.setCancelable(false);

            progressDialog.setTitle("Loading Apps");
            progressDialog.show();
            new AsyncGetData(MainActivity.this).execute("http://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");

        } else {
            Toast.makeText(MainActivity.this, "NO Internet Please Check Connection", Toast.LENGTH_LONG).show();

        }
        Button b=findViewById(R.id.buttonFilter);
        b.setText("Filter");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Choose Genre");

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
    public void handleData(final ArrayList<App> applist) {

        progressDialog.dismiss();
        if(applist.size()==0)
        {

            Toast.makeText(MainActivity.this, "No Apps Found", Toast.LENGTH_SHORT).show();
        }
        else {
            ListofApps = new ArrayList<>();
            ListView displayApps = findViewById(R.id.listViewApps);
            AppsAdapter appsAdapter = new AppsAdapter(this, R.layout.display_news, applist);
            displayApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("apps", applist.get(position));
                    // intent.putExtra("Category",categories[position]);
                    startActivity(intent);

                }
            });
            displayApps.setAdapter(appsAdapter);
            String sizee= String.valueOf(applist.size());
            genre.setText(sizee);
            Log.d("test", applist.toString());
          //  Log.d("demo",AsyncGetData.)
        }
    }

}
