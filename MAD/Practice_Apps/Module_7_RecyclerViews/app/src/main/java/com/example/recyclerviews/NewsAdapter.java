package com.example.recyclerviews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by akki0923 on 3/12/18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<Article> nData;

    public NewsAdapter(ArrayList<Article> nData) {
        this.nData = nData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_news, parent, false);
        ViewHolder viewHolder= new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
    Article article=nData.get(position);

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

            Context context= viewHolder.ImageViewArticle.getContext();
            Picasso.with(context).load(R.drawable.noimage).into(viewHolder.ImageViewArticle);
        }
        else
        {
            Context context= viewHolder.ImageViewArticle.getContext();
            Picasso.with(context).load(article.urltoimage).into(viewHolder.ImageViewArticle);

        }

        viewHolder.article=article;
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }
    public void delete(int position) { //removes the row
        nData.remove(position);
        notifyItemRemoved(position);
    }


    public  class ViewHolder extends   RecyclerView.ViewHolder{
      TextView textViewTitle,textViewPublishedAt;
      ImageView ImageViewArticle;
      ImageButton imageButton;
        Article article;

        public ViewHolder(View itemView) {
           super(itemView);
           textViewTitle=itemView.findViewById(R.id.textViewArticleTitle);
           textViewPublishedAt=itemView.findViewById(R.id.textViewArticlePublishedAt);
           ImageViewArticle=itemView.findViewById(R.id.imageViewArticleImage);
        /*    imageButton=itemView.findViewById(R.id.imageButtonStar);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context c = view.getContext();
                    Toast.makeText(c, "Hellooooo", Toast.LENGTH_SHORT).show();
                    Picasso.with(c).load(String.valueOf(c.getResources().getDrawable(android.R.drawable.btn_star_big_on))
                    ).into(imageButton);

                }
            });*/

           itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {

                   delete(getPosition());
                   Context c = view.getContext();
                   Toast.makeText(c, "Hello", Toast.LENGTH_SHORT).show();

                   return false;
               }
           });
       }


    }


}
