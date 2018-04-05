package com.example.inclass08;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,DisplayFragment.OnDisplayFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new MainFragment(),"Main_Fragment").commit();

    }

    @Override
    public void onFragmentInteraction(Student student) {
        Student display_student= student;

        Log.d("demo",display_student.toString());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,DisplayFragment.newInstance(display_student),"Display_Fragment").commit();
      //  Fragment fragment= getFragmentManager().findFragmentByTag("Display_Fragment");
      //  fragment.SetDisplay(display_student);
    }
}
