package com.example.apps_itunes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView title,rdate,artist,copyright,genre;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle("Detail Activity");
        title = findViewById(R.id.textViewAppTitle);
        rdate = findViewById(R.id.textViewRDate);
        artist = findViewById(R.id.textViewDArtist);
        copyright = findViewById(R.id.textViewCopyRight);
        genre=findViewById(R.id.textViewDGenre);
        image = findViewById(R.id.selectedImage);
        //description = findViewById(R.id.textViewDGenre);

        App app = (App) getIntent().getExtras().getSerializable("apps");

        if (app.appName.isEmpty()||app.appName==null)
        {
            title.setText("No Name");
        }
        else
        {
            title.setText(app.appName);
        }
        if (app.copyRight.isEmpty()||app.copyRight==null)
        {
            copyright.setText("No CopyRight");
        }
        else
        {
            copyright.setText(app.copyRight);
        }
        //artist
        if (app.artistName.isEmpty()||app.artistName==null)
        {
            artist.setText("No Artist");
        }
        else
        {
            artist.setText(app.artistName);
        }
        //date
        if (app.releaseDate.isEmpty()||app.releaseDate==null)
        {
            rdate.setText("No Date");
        }
        else
        {
            rdate.setText(app.releaseDate);
        }
        //image
        if (app.imageUrl.isEmpty()||app.imageUrl.equals("null")||app.imageUrl==null)
        {
            Picasso.with(this  ).load(R.drawable.noimage).into(image);
        }
        else
        {
            Picasso.with(this).load(app.imageUrl).into(image);

        }

        if (app.genre.isEmpty()||app.genre.equals("null")||app.genre==null) {
            genre.setText("No Genre");

        }
        else
        {
            StringBuilder sb;
            sb=new StringBuilder();
            for(int i=0;i<app.genre.size();i++)
            {

                sb.append(app.genre.get(i));
                sb.append(",");

            }
           genre.setText(sb.toString());

        }


    }
}