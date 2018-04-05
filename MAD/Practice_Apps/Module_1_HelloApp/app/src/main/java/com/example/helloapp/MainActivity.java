package com.example.helloapp;

http://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);


        Button btn = (Button)findViewById(R.id.buttonOK);
        Log.d("demo","Button Text is "+ btn.getText().toString());

        btn.setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);

    rg=findViewById(R.id.radioGroup);
    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedid) {
           RadioButton rb=findViewById(checkedid);
           Log.d("demo","The Color Checked is :"+rb.getText().toString());

        }
    });

    findViewById(R.id.checkedcolor).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = rg.getCheckedRadioButtonId();
            if (id == R.id.buttonRed) {
                Log.d("demo","The Color Selected is Red");
            } else if (id == R.id.buttonGreen) {
                Log.d("demo","The Color Selected is Green");
            } else if (id == R.id.buttonBlue) {
                Log.d("demo","The Color Selected is Blue");
            } else if (id == -1)
            {
                Log.d("demo","No Color is Selected");
            }
        }
    });

        SeekBar sb= findViewById(R.id.seekBar);
        final TextView tv= findViewById(R.id.textViewProgress);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tv.setText(progress +"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        int id= view.getId();

        if(id==R.id.buttonOK) {
            Log.d("demo","OK Button Clicked");

        }
        else if(id==R.id.buttonCancel){

            Log.d("demo","Cancel Button Clicked");
        }

    }
}
/*Log.d("demo", "HELLO ALL");
        Log.w("demo","This is Warning");

        String s= getResources().getString(R.string.are_you_sure);
        Log.d("demo",s);

        String[] colors= getResources().getStringArray(R.array.colors_array);

        for (String str:colors) {
        Log.d("demo",str);
        }*/
