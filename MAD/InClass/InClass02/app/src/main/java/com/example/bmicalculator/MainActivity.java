package com.example.bmicalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView IAge,IWeight,IFeet,IInches,Result,BMI,Status,Range,Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        Button btn = (Button)findViewById(R.id.buttonCalculate);
        btn.setOnClickListener(this);

        IAge = findViewById(R.id.editTextAge);
        IWeight=findViewById(R.id.editTextWeight);
        IFeet=findViewById(R.id.editTextFeet);
        IInches=findViewById(R.id.editTextInches);
        Result=findViewById(R.id.textViewResult);
        BMI= findViewById(R.id.textViewBMI);
        Status=findViewById(R.id.textViewStatus);
        Range=findViewById(R.id.textViewRange);
        Message=findViewById(R.id.textViewMessage);
    }

    @Override
    public void onClick(View view) {
        int age,weight,feet,inches;
        double bmi;
        try{
            age= Integer.parseInt(IAge.getText().toString());
            weight= Integer.parseInt(IWeight.getText().toString());
            feet= Integer.parseInt(IFeet.getText().toString());
            inches= Integer.parseInt(IInches.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Toast t=Toast.makeText(getApplicationContext(),"Input Cannot be Empty",Toast.LENGTH_SHORT);
            t.show();
            age=weight=feet=inches=1;
           return;
        }

        if(age<18)
        {
            Toast t=Toast.makeText(getApplicationContext(),"Age Should be Greater than 18 years",Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            int totalinches = (feet * 12) + inches;
            double temp = totalinches;
            double temp2 = weight;

            double high = 25.0 * (totalinches * totalinches) / 703.0;
            double low = 18.5 * (totalinches * totalinches) / 703.0;
            low = Math.round(low * 100.0) / 100.0;

            high = Math.round(high * 100.0) / 100.0;
            double gain= (low-weight);
            gain=Math.round(gain*100.0)/100.0;
            bmi = 703.0 * (temp2 / (temp * temp));
            bmi = Math.round(bmi * 100.0) / 100.0;
            if (bmi < 18.5) {
                Result.setText("RESULT");
                BMI.setText("BMI = " + bmi);
                Status.setText("(UnderWeight)");
                Status.setTextColor(Color.RED);
                Range.setText("Normal BMI range: 18.5 - 25");
                Message.setText("You will need to gain " +gain + "lb to reach a BMI of 18.5");
            } else if (18.5 <= bmi && bmi <= 25) {
                Result.setText("RESULT");
                BMI.setText("BMI = " + bmi);
                Status.setText("(Normal)");
                Status.setTextColor(Color.GREEN);
                Range.setText("Normal BMI range: 18.5 - 25");
                Message.setText("Keep Up the Good Work! ");

            } else if (25 <= bmi && bmi < 30) {

                Result.setText("RESULT");
                BMI.setText("BMI = " + bmi);
                Status.setText("(OverWeight)");
                Status.setTextColor(Color.BLUE);
                Range.setText("Normal BMI range: 18.5 - 25");
                Message.setText("You will need to loose " + (weight - high) + "lb to reach a BMI of 25");
            } else if (bmi >= 30)

            {
                Result.setText("RESULT");
                BMI.setText("BMI = " + bmi);
                Status.setText("(Obese)");
                Status.setTextColor(Color.BLUE);
                Range.setText("Normal BMI range: 18.5 - 25");
                Message.setText("You will need to loose " + (weight - high) + "lb to reach a BMI of 25");
            }


        }


    }
}
