package com.example.inclass07;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView title;
    TextView published;
    ImageView image;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailll);
        this.setTitle("Detail Activity");
        title = findViewById(R.id.textViewTitle);
        published = findViewById(R.id.textViewPublishedAt);
        image = findViewById(R.id.selectedImage);
        description = findViewById(R.id.textViewDescription);

        Article article = (Article) getIntent().getExtras().getSerializable("article");

        if (article.title.isEmpty()||article.title==null)
        {
            title.setText("No Title");
        }
        else
        {
            title.setText(article.title);
        }
        if (article.publishedat.isEmpty()||article.publishedat==null)
        {
            published.setText("No Published Date");
        }
        else
        {
            published.setText(article.publishedat);
        }

        if (article.urltoimage.isEmpty()||article.urltoimage.equals("null")||article.urltoimage==null)
        {
           image.setImageDrawable(getDrawable(R.drawable.noimage));
        }
        else
        {
            Picasso.with(this).load(article.urltoimage).into(image);
        }

        if (article.description.isEmpty()||article.description.equals("null"))
        {
            description.setText("No Description");
        }
        else
        {
            description.setText(article.description);
        }


    }
}