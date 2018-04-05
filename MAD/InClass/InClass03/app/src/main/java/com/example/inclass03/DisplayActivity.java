package com.example.inclass03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.inclass03.MainActivity.STUDENT_KEY;

public class DisplayActivity extends AppCompatActivity {
TextView rname,remail,rdept,rmood;
static final int NAME_CODE=100;
static final int EMAIL_CODE=200;
    static final int DEPT_CODE=300;
    static final int MOOD_CODE=400;
   static Student student;
   static String EDIT_KEY="a";



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        this.setTitle("Display Activity");
        student = getIntent().getExtras().getParcelable(STUDENT_KEY);
        if(getIntent()!=null && getIntent().getExtras()!=null) {


            rname=findViewById(R.id.textViewIname);
            remail=findViewById(R.id.textViewIemail);
            rdept=findViewById(R.id.textViewDept);
            rmood=findViewById(R.id.textViewImood);

            rname.setText((CharSequence) student.name);
            remail.setText(student.email);
            rdept.setText(student.department);
            rmood.setText(Double.toString(student.mood)+ "% Positive");

            findViewById(R.id.edit_name).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(STUDENT_KEY,student);
                    intent.putExtra(EDIT_KEY,"name");
                    startActivityForResult(intent,NAME_CODE);

                }
            });

            findViewById(R.id.edit_email).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(STUDENT_KEY,student);
                    intent.putExtra(EDIT_KEY,"email");
                    startActivityForResult(intent,EMAIL_CODE);
                }
            });

            findViewById(R.id.edit_department).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(STUDENT_KEY,student);
                    intent.putExtra(EDIT_KEY,"dept");
                    startActivityForResult(intent,DEPT_CODE);
                }
            });

            findViewById(R.id.edit_mood).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(STUDENT_KEY,student);
                    intent.putExtra(EDIT_KEY,"mood");
                    startActivityForResult(intent,MOOD_CODE);
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(requestCode==NAME_CODE)
       {
           if(resultCode==RESULT_OK)
           { Student new_student=data.getExtras().getParcelable(MainActivity.STUDENT_KEY);
               new_student=data.getExtras().getParcelable(MainActivity.STUDENT_KEY);

               rname.setText((CharSequence) new_student.name);
               student=new_student;
           }
       }
       else if(requestCode==EMAIL_CODE){
           if(resultCode==RESULT_OK)
           {final Student new_student=data.getExtras().getParcelable(MainActivity.STUDENT_KEY);
               student=new_student;
               remail.setText((CharSequence) new_student.email);
           }
       }
       else if (requestCode==DEPT_CODE){
           if(resultCode==RESULT_OK)
           {final Student new_student=data.getExtras().getParcelable(MainActivity.STUDENT_KEY);
               student=new_student;
               rdept.setText((CharSequence) new_student.department);
           }
       }
       else if (requestCode==MOOD_CODE){ if(resultCode==RESULT_OK)
       {final Student new_student=data.getExtras().getParcelable(MainActivity.STUDENT_KEY);
           student=new_student;
           rmood.setText(Double.toString(new_student.mood) +"% Positive" );
       }}
    }
}
