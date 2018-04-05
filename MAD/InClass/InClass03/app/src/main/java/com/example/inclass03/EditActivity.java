package com.example.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
String edit_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        this.setTitle("Edit Activity");
        edit_key=getIntent().getExtras().getString(DisplayActivity.EDIT_KEY);
        final Student editstudent=getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);
        findViewById(R.id.editTextName).setVisibility(View.INVISIBLE);
        findViewById(R.id.editTextEmail).setVisibility(View.INVISIBLE);
        findViewById(R.id.RadioGroupDept).setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewDept).setVisibility(View.INVISIBLE);
        findViewById(R.id.textViewMood).setVisibility(View.INVISIBLE);
        findViewById(R.id.seekBarMood).setVisibility(View.INVISIBLE);
        final EditText dname=findViewById(R.id.editTextName);
        final EditText demail=findViewById(R.id.editTextEmail);
        dname.setText(editstudent.name);
        demail.setText(editstudent.email);
        final RadioGroup rg= findViewById(R.id.RadioGroupDept);
       final SeekBar sb=findViewById(R.id.seekBarMood);
        //Button b=findViewById(R.id.buttonSave);
       // b.setText(edit_key);
        if(edit_key.equals("name"))
        {

                    dname.setVisibility(View.VISIBLE);



        }
        else if(edit_key.equals("email"))
        {

                    demail.setVisibility(View.VISIBLE);

        }
        else if(edit_key.equals("dept"))
        {
            findViewById(R.id.textViewDept).setVisibility(View.VISIBLE);

                    rg.setVisibility(View.VISIBLE);

            if (editstudent.department.equals("SIS")) {

                RadioButton r=findViewById(R.id.radioButtonSIS);
                r.setChecked(true);
            } else if (editstudent.department.equals("CS")) {

                RadioButton r=findViewById(R.id.radioButtonCS);
                r.setChecked(true);
            } else if (editstudent.department.equals("BIO")) {
                RadioButton r=findViewById(R.id.radioButtonBIO);
                r.setChecked(true);
            }
            else if (editstudent.department.equals("Others")) {
                RadioButton r=findViewById(R.id.radioButtonOthers);
                r.setChecked(true);
            }
        }
        else if(edit_key.equals("mood")) {
            findViewById(R.id.textViewMood).setVisibility(View.VISIBLE);
            findViewById(R.id.seekBarMood).setVisibility(View.VISIBLE);

            sb.setProgress((int) editstudent.mood);
        }

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // EditText fname = findViewById(R.id.editTextName);
                //EditText email = findViewById(R.id.editTextEmail);
                String a=dname.getText().toString();
                String b=demail.getText().toString();
                if((a==null||a=="")||(b==null||b=="")){
                        //setResult(RESULT_CANCELED);
                    Toast t=Toast.makeText(getApplicationContext(),"Input Cannot be Empty",Toast.LENGTH_SHORT);
                    t.show();
                }
                else{
                    Intent intent = new Intent();
                    Student new_student;
                    if(edit_key.equals("name")){
                        editstudent.name=a;


                    }
                    else if(edit_key.equals("email")){
                        editstudent.email=b;

                    }
                    else if (edit_key.equals("dept"))
                    {

                        int id = rg.getCheckedRadioButtonId();
                        String dept = "";
                        if (id==R.id.radioButtonSIS) {
                            // Log.d("demo","The Color Selected is Red");
                            dept = "SIS";
                        } else if (id == R.id.radioButtonCS) {
                            dept="CS";
                            //Log.d("demo","The Color Selected is Green");
                        } else if (id == R.id.radioButtonBIO) {
                            dept="BIO";
                            //Log.d("demo", "The Color Selected is Blue");
                        }
                        else if (id == R.id.radioButtonOthers) {
                            dept="Others";
                            // Log.d("demo","The Color Selected is Blue");
                        }

                        editstudent.department=dept;
                    }
                    else if(edit_key.equals("mood"))
                    {
                        double dmood=sb.getProgress();
                        editstudent.mood=dmood;

                    }


                    intent.putExtra(MainActivity.STUDENT_KEY,editstudent);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });


    }
}
