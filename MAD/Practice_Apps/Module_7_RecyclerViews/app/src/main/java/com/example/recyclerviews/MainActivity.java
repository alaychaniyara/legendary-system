package com.example.recyclerviews;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AsyncGetData.IData {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Random r= new Random();
       // String[] sc= getResources().getStringArray(R.array.categories);
       // int rno=r.nextInt(6);
       // String selected_category=sc[rno];
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new AsyncGetData(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=6dfeb886d9da4ed7a6538962a7a5a6ae");
       progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading News");
        progressDialog.show();
    }

    @Override
    public void handleData(final ArrayList<Article> articles) {
    progressDialog.dismiss();

        mAdapter = new NewsAdapter(articles);
        mRecyclerView.setAdapter(mAdapter);
   /*     mRecyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int id=view.getId();
                articles.remove(id);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });
    */}
}
