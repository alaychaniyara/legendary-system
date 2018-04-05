package com.example.inclass07;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akki0923 on 2/26/18.
 */

public class NewsAdapter extends ArrayAdapter<Article> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<Article> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    ViewHolder viewHolder;
        Article article=getItem(position);
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.display_news,parent,false);
            viewHolder=new ViewHolder();
           viewHolder.textViewTitle= convertView.findViewById(R.id.textViewArticleTitle);
            viewHolder.textViewPublishedAt= convertView.findViewById(R.id.textViewArticlePublishedAt);
            viewHolder.ImageViewArticle= convertView.findViewById(R.id.imageViewArticleImage);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
    //    viewHolder.textViewTitle.setText(article.title);
      //  viewHolder.textViewPublishedAt.setText(article.publishedat);
       // Picasso.with(getContext()).load(article.urltoimage).into(viewHolder.ImageViewArticle);

        if (article.title.isEmpty()||article.title==null)
        {
            viewHolder.textViewTitle.setText("No Title");
        }
        else
        {
            viewHolder.textViewTitle.setText(article.title);
        }
        if (article.publishedat.isEmpty()||article.publishedat==null)
        {
            viewHolder.textViewPublishedAt.setText("No Published Date");
        }
        else
        {
            viewHolder.textViewPublishedAt.setText(article.publishedat);
        }

        if (article.urltoimage.isEmpty()||article.urltoimage.equals("null")||article.urltoimage==null)
        {
            Picasso.with(getContext()).load(R.drawable.noimage).into(viewHolder.ImageViewArticle);
        }
        else
        {
            Picasso.with(getContext()).load(article.urltoimage).into(viewHolder.ImageViewArticle);

        }

        return convertView;
    }

    private static class ViewHolder
    {
        TextView textViewTitle;
        TextView textViewPublishedAt;
        ImageView ImageViewArticle;

    }
}
