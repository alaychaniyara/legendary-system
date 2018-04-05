package com.example.inclass03;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
/* Assignment InClass03
    InClass03.zip
    Alay Chaniyara
    Adam Burnam
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText name, email;
    RadioGroup department;
    SeekBar mood;
    String selected;

    static String STUDENT_KEY="STUDENT";
    String NAME_KEY="NAME";
    String EMAIL_KEY="EMAIL";
    String DEPARTMENT_KEY="DEPARTMENT";
    String MOOD_KEY="MOOD";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Main Activity");
        Button btn = (Button) findViewById(R.id.buttonSubmit);
        btn.setOnClickListener(this);

        name=findViewById(R.id.editTextName);
        email=findViewById(R.id.editTextEmail);
        department = findViewById(R.id.RadioGroupDept);
        mood=findViewById(R.id.seekBarMood);



    }

    @Override
    public void onClick(View view) {

        String iname,iemail,idepartment;
        double imood;
        iname= name.getText().toString();
        iemail= email.getText().toString();
        imood=mood.getProgress();
        int id = department.getCheckedRadioButtonId();
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
        } else if (id == -1)
        {
            //Log.d("demo","No Department is Selected");
        }


        if((iname==null||iname=="")||(iemail==null||iemail=="")||(id==-1))
        {
            Toast t=Toast.makeText(getApplicationContext(),"Input Cannot be Empty",Toast.LENGTH_SHORT);
            t.show();
            //idepartment= Integer.parseInt(department.getText().toString());
            //imood= Integer.parseInt(IInches.getText().toString());
        }
        else
        {
            Intent intent= new Intent(MainActivity.this,DisplayActivity.class);

            intent.putExtra(STUDENT_KEY,new Student(iname,iemail,dept,imood));

            startActivity(intent);


        }

    }
}