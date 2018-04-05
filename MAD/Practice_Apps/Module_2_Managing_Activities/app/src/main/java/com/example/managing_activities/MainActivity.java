package com.example.managing_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.d("demo","Inside On Create");
        RelativeLayout relativeLayout=new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(relativeLayout);

        TextView textView= new TextView(this);
        textView.setText(R.string.helloworld);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setId(100);
        relativeLayout.addView(textView);

        Button button=new Button(this);
        button.setText(R.string.clickbutton);
        RelativeLayout.LayoutParams buttonLayoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.BELOW,textView.getId());
        button.setLayoutParams(buttonLayoutParams);
        relativeLayout.addView(button);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("demo","Inside OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("demo","Inside On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("demo","Inside On Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("demo","Inside On Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo","Inside On Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
