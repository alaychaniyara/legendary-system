package com.example.rubby.inclass10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rubby on 4/4/2018.
 */

public class ThreadsAdapter extends ArrayAdapter<Threads.Thread> {
    public ThreadsAdapter(@NonNull Context context, int resource, @NonNull List<Threads.Thread> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        Threads.Thread thread=getItem(position);

        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.display_thread,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.textViewThreadName= convertView.findViewById(R.id.textViewThreadName);
            viewHolder.buttonDeleteThread = convertView.findViewById(R.id.buttonDeleteThread);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewThreadName.setText(thread.getTitle());

        return convertView;
    }

    private static class ViewHolder
    {
        TextView textViewThreadName;
        Button buttonDeleteThread;

    }
}
