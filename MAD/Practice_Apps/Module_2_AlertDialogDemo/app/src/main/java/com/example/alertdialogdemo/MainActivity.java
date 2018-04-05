package com.example.alertdialogdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
CharSequence[] items= {"Red","Blue","Green","Yellow"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final ProgressDialog progress=new ProgressDialog(this);
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        progress.show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a Color")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Log.d("demo","Selected "+items[which]);
                    }
                })
                .setCancelable(false);
        /*
        //for multiple checkboxes
            .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which, boolean checked) {
                 Log.d("demo","Selected "+items[which]+"was"+isChecked);
                }
            })*/
                /*

                //for Showing Radio Buttons
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    Log.d("demo","Selected "+items[which]);
                    }
                })*/

        //FOR SELECTING YES OR NO OR CANCEL OR NOt CANCEL TyPE OF BUttoNs
        /*
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("demo","Clicked OK");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("demo","Clicked Cancel");
                    }
                });
*/
        final AlertDialog alert=builder.create();

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });


    }
}
