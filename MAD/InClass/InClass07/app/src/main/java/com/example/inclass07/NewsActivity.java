package com.example.inclass07;

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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements AsyncGetData.IData{
    ArrayList<Article> detailArticle;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        String selected_category = getIntent().getExtras().getString("Category");
        this.setTitle(selected_category);
        progressDialog = new ProgressDialog(this);
        if (isConnected()) {
            Toast.makeText(NewsActivity.this, "Internet Connected", Toast.LENGTH_LONG).show();

            progressDialog.setCancelable(false);

            progressDialog.setTitle("Loading News");
            progressDialog.show();
            new AsyncGetData(NewsActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&category=" + selected_category + "&apiKey=7891d088b1fa4ee7beb4acf93b7659ec");


        } else {
            Toast.makeText(NewsActivity.this, "NO Internet Please Check Connection", Toast.LENGTH_LONG).show();

        }

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
    public void handleData(final ArrayList<Article> articles) {
        progressDialog.dismiss();

        if(articles.size()==0)
        {

            Toast.makeText(NewsActivity.this, "No News Found", Toast.LENGTH_SHORT).show();
        }
        else {
            detailArticle = new ArrayList<>();
            ListView displayArticles = findViewById(R.id.listViewDisplayArticles);
            NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.display_news, articles);
            displayArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(NewsActivity.this, DetailActivity.class);
                    intent.putExtra("article", articles.get(position));
                    // intent.putExtra("Category",categories[position]);
                    startActivity(intent);

                }
            });
            displayArticles.setAdapter(newsAdapter);
            Log.d("demo", articles.toString());
        }
    }
}
