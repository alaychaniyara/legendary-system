package com.example.inclass07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
* InClass07
* NEWS APP FOR LIST VIEW
* AlAY CHANIYARA
* ADAM BURNAM*/
public class MainActivity extends AppCompatActivity {
    String[] categories={"business","entertainment","general","health","science","sports","technology"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Categories");


        ListView listView = (ListView)findViewById(R.id.listViewCategory);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, categories);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra("Category",categories[position]);
                startActivity(intent);
            //    Log.d("demo", "Clicked item " + position + ", color " + colors[position]);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
}
