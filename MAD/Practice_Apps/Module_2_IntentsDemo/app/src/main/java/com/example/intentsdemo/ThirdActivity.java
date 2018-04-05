package com.example.intentsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Third Activity");
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
/* Passing data using values
            Toast.makeText(this, getIntent().getExtras().
                            getString(MainActivity.NAME_KEY)+
                    ", AGE " +getIntent().getExtras().getDouble(MainActivity.AGE_KEY),
                    Toast.LENGTH_LONG).show();*/

//passing values using serializable.
 //   User user= (User)getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
   //     Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show();

  //passing value using parcelable

            Person person= getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this, (CharSequence) person.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
