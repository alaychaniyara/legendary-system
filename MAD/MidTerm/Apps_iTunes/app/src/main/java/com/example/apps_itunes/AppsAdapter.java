package com.example.apps_itunes;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akki0923 on 3/12/18.
 */

public class AppsAdapter extends ArrayAdapter<App>{
    ArrayList<String> unqGenre =new ArrayList<String>();

    public AppsAdapter(@NonNull Context context, int resource, @NonNull List<App> objects) {
        super(context, resource, objects);
    }
    StringBuilder sb=new StringBuilder();

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        App app=getItem(position);
        unqGenre.add("All");
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.display_news,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textViewName= convertView.findViewById(R.id.textViewAppsName);
            viewHolder.textViewArtist= convertView.findViewById(R.id.textViewDArtist);
            viewHolder.textViewRelease= convertView.findViewById(R.id.textViewDate);
            viewHolder.textViewGenre= convertView.findViewById(R.id.textViewGenres);
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
//appname
        if (app.appName.isEmpty()||app.appName==null)
        {
            viewHolder.textViewName.setText("No Name");
        }
        else
        {
            viewHolder.textViewName.setText(app.appName);
        }
        //artist
        if (app.artistName.isEmpty()||app.artistName==null)
        {
            viewHolder.textViewArtist.setText("No Artist");
        }
        else
        {
            viewHolder.textViewArtist.setText(app.artistName);
        }
        //date
        if (app.releaseDate.isEmpty()||app.releaseDate==null)
        {
            viewHolder.textViewRelease.setText("No Date");
        }
        else
        {
            viewHolder.textViewRelease.setText(app.releaseDate);
        }
        //image
        if (app.imageUrl.isEmpty()||app.imageUrl.equals("null")||app.imageUrl==null)
        {
            Picasso.with(getContext()).load(R.drawable.noimage).into(viewHolder.ImageViewArticle);
        }
        else
        {
            Picasso.with(getContext()).load(app.imageUrl).into(viewHolder.ImageViewArticle);

        }




        if (app.genre.isEmpty()||app.genre.equals("null")||app.genre==null) {
            viewHolder.textViewGenre.setText("No Genre");

        }
        else
        {
            sb=new StringBuilder();
            for(int i=0;i<app.genre.size();i++)
            {

                sb.append(app.genre.get(i));
                sb.append(",");

            }

            viewHolder.textViewGenre.setText(sb.toString());

        }
        return convertView;
    }

    private static class ViewHolder
    {
        TextView textViewName;
        TextView textViewRelease;
        TextView textViewGenre;
        TextView textViewArtist;
        ImageView ImageViewArticle;

    }
}
