package com.example.inclass06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
* InClass 06
* News Viewer App
* Group 22
* Alay Chaniyara
   * Adam Burnam
* */
public class MainActivity extends AppCompatActivity implements AsyncGetData.IData {
    TextView searchCategory,imageID,setTitle,setPublished,setDescription;
    Button buttonGo;
    ImageView selectedImage;
    Button buttonPrev;
    Button buttonNext;
    ArrayList<Article> articlesdisplay;

    String[] categories={"business","entertainment","general","health","science","sports","technology"};
    int imageIndex;
     ProgressDialog progressDialog;

    AlertDialog.Builder CategoryAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");

        imageID=findViewById(R.id.textViewImageID);
        searchCategory = findViewById(R.id.searchCategory);
        buttonGo = findViewById(R.id.buttonGo);
        selectedImage = findViewById(R.id.selectedImage);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonNext = findViewById(R.id.buttonNext);
        setTitle= findViewById(R.id.textViewTitle);
        setDescription= findViewById(R.id.textViewDescription);
        setPublished=findViewById(R.id.textViewPublishedAt);
        progressDialog = new ProgressDialog(this);
        buttonPrev.setEnabled(false);
        buttonNext.setEnabled(false);

        searchCategory.setText("Show Categories");

        if(isConnected()) {
            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(MainActivity.this, "NO Internet Please Check Connection", Toast.LENGTH_LONG).show();

        }

        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CategoryAlert = new AlertDialog.Builder(MainActivity.this);
                CategoryAlert.setCancelable(false);
                CategoryAlert.setTitle("Choose Category").setItems(categories, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                   searchCategory.setText(categories[i]);
                        if(isConnected()) {
                            Toast.makeText(MainActivity.this, "Internet Connected", Toast.LENGTH_LONG).show();
                            new  AsyncGetData(MainActivity.this).execute("https://newsapi.org/v2/top-headlines?country=us&category="+categories[i]+"&apiKey=b61ff8ce1f694c01ba503be451144848");
                            progressDialog.setCancelable(false);

                            progressDialog.setTitle("Loading News");
                            progressDialog.show();
                            imageIndex=0;

                        }
                        else {
                            searchCategory.setText("Show Categories");
                            setTitle.setText("");
                            setPublished.setText("");
                            setDescription.setText("");
                            Picasso.with(MainActivity.this).load(R.drawable.tranparent).into(selectedImage);
                            imageID.setText("News 0 Of 0");

                            Toast.makeText(MainActivity.this, "NO Internet Please Check Connection", Toast.LENGTH_LONG).show();

                        }

                    }
            });
                        CategoryAlert.show();

            }

        });


        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageIndex > 0) {
                    imageIndex--;
               setDataonActivity();
                }
                else{

                    imageIndex = articlesdisplay.size() - 1;
               setDataonActivity();
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageIndex <articlesdisplay.size()-1){
                    imageIndex++;

             setDataonActivity();
                }
                else{

                    imageIndex = 0;
               setDataonActivity();

                }
            }
        });
    }


public void setDataonActivity(){

    if(articlesdisplay.get(imageIndex).title.equals("null"))
    {
    setTitle.setText("");
        setPublished.setText(articlesdisplay.get(imageIndex).publishedat);
        setDescription.setText(articlesdisplay.get(imageIndex).description);
        Picasso.with(this).load(articlesdisplay.get(imageIndex).urltoimage).into(selectedImage);
        imageID.setText("News " + (imageIndex + 1) + " Of " + articlesdisplay.size());
        Log.d("test", articlesdisplay.get(imageIndex).toString());
    }
    if(articlesdisplay.get(imageIndex).publishedat.equals("null"))
    {
        setPublished.setText("");
        setTitle.setText(articlesdisplay.get(imageIndex).title);
        setDescription.setText(articlesdisplay.get(imageIndex).description);
        Picasso.with(this).load(articlesdisplay.get(imageIndex).urltoimage).into(selectedImage);
        imageID.setText("News " + (imageIndex + 1) + " Of " + articlesdisplay.size());
        Log.d("test", articlesdisplay.get(imageIndex).toString());

    }
    if(articlesdisplay.get(imageIndex).description.equals("null"))
        {
            setDescription.setText("");
            setTitle.setText(articlesdisplay.get(imageIndex).title);
            setPublished.setText(articlesdisplay.get(imageIndex).publishedat);
            Picasso.with(this).load(articlesdisplay.get(imageIndex).urltoimage).into(selectedImage);
            imageID.setText("News " + (imageIndex + 1) + " Of " + articlesdisplay.size());
            Log.d("test", articlesdisplay.get(imageIndex).toString());

        }
        else {
        setTitle.setText(articlesdisplay.get(imageIndex).title);
        setPublished.setText(articlesdisplay.get(imageIndex).publishedat);
        setDescription.setText(articlesdisplay.get(imageIndex).description);
        Picasso.with(this).load(articlesdisplay.get(imageIndex).urltoimage).into(selectedImage);
        imageID.setText("News " + (imageIndex + 1) + " Of " + articlesdisplay.size());
        Log.d("test", articlesdisplay.get(imageIndex).toString());
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
    public void handleData(ArrayList<Article> articles) {

    articlesdisplay= new ArrayList<>();
    articlesdisplay=articles;
        if (articles.size()==0)
        {

            progressDialog.dismiss();

            Toast.makeText(MainActivity.this, "No Articles to display", Toast.LENGTH_SHORT).show();

            setTitle.setText("");
            setPublished.setText("");
            setDescription.setText("");
            Picasso.with(this).load(R.drawable.tranparent).into(selectedImage);
            imageID.setText("News 0 of 0");


        }
        else
            {

                if (articles.size()>1) {
                    buttonPrev.setEnabled(true);
                    buttonNext.setEnabled(true);
                }
                progressDialog.dismiss();
            //    articlesdisplay=articles;
                    setDataonActivity();
           }
    }


}
