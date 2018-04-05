 package com.example.intentsdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

 public class MainActivity extends AppCompatActivity {
    static String NAME_KEY="Name",AGE_KEY="Age",USER_KEY="USER",PERSON_KEY="person";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("http://uncc.edu"));
            startActivity(i);
        findViewById(R.id.button2Second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TO use explicit intent
                //  Intent intent= new Intent(MainActivity.this,SecondActivity.class);
                //to use implicit intent
                Intent intent= new Intent("com.example.intentsdemo.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);

            }
        });

        findViewById(R.id.button2Third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,ThirdActivity.class);

                //pass values using the single datatype for each entry
                // intent.putExtra(NAME_KEY,"WELCOME ALAY CHANIYARA");
                //intent.putExtra(AGE_KEY,(double)23.0);
                //passing values through intent using the serializable class values
                //User user=new User("AKSHAY KUMAR", 50.0);
                //intent.putExtra(USER_KEY,user);
                //passing values in intents using the parcelable interfaces
                intent.putExtra(PERSON_KEY,new Person("Akshay Kumar",50.0,"INDIA" ));
                startActivity(intent);

            }
        });
    }
}
