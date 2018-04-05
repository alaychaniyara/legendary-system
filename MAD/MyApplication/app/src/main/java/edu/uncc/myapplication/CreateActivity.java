/* Group 34
   Name: Akshay Karai, Naga Poorna Pujitha
   Homework assignment 2
* */

package edu.uncc.myapplication;


        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Parcelable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {


    EditText displayDate;
    EditText displayTime;
    String format;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_create);
        super.onCreate(savedInstanceState);

        final EditText editText = (EditText) findViewById(R.id.editTextTitle);
        displayDate = (EditText) findViewById(R.id.editTextDate);
        displayTime = (EditText) findViewById(R.id.editTextTime);
        final RadioGroup radioButtonGroup =(RadioGroup) findViewById(R.id.radioGroup) ;
        Button button1 = findViewById(R.id.saveButton);

        String flag = getIntent().getExtras().getString("flag");

        if(flag.equals("edit")){
            setTitle("Edit Task");
            Task t = (Task) getIntent().getExtras().get("task");
            editText.setText(t.getTitle());
            displayDate.setText(t.getDate());
            displayTime.setText(t.getTime());
            ((RadioButton)findViewById(t.getPriorityRadio())).setChecked(true);

        }
        displayTime.setKeyListener(null);
        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar time = Calendar.getInstance();
                int  hour = time.get(Calendar.HOUR_OF_DAY);
                int minute = time.get(Calendar.MINUTE);
                TimePickerDialog dialogTime = new TimePickerDialog(CreateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour,minute,false);
                dialogTime.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogTime.show();
            }
        });

        displayDate.setKeyListener(null);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogDate = new DatePickerDialog(
                        CreateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialogDate.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogDate.show();
            }

        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if (hour == 0){
                    hour += 12;
                    format = "AM";
                }else if (hour == 12){
                    format = "PM";
                }else if (hour > 12){
                    hour -=12;
                    format = "PM";
                }else{
                    format = "AM";
                }
                 String Time = (hour<10?"0"+hour:hour) + ":" + (minute<10?"0"+minute:minute) +" "+ format;
                 displayTime.setText(Time);
            }
        };

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                displayDate.setText(date);
            }

        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checkedRadio = findViewById(radioButtonGroup.getCheckedRadioButtonId());
                String priority = checkedRadio.getText().toString();
                String dateSelected = displayDate.getText().toString();
                String timeSelected = displayTime.getText().toString();
                String title = editText.getText().toString();

                if(title == null || title.isEmpty()){
                    Toast.makeText(CreateActivity.this, "Enter a title.", Toast.LENGTH_SHORT).show();
                }else if(title.length()>20){
                    Toast.makeText(CreateActivity.this, "Title must not exceed 20 characters.", Toast.LENGTH_SHORT).show();
                }else if(dateSelected == null || dateSelected.isEmpty()){
                    Toast.makeText(CreateActivity.this, "Select a date.", Toast.LENGTH_SHORT).show();
                }else if(timeSelected == null || timeSelected.isEmpty()){
                    Toast.makeText(CreateActivity.this, "Select a time.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CreateActivity.this,MainActivity.class);
                    intent.putExtra(MainActivity.DATE_KEY,dateSelected);
                    intent.putExtra(MainActivity.TIME_KEY,timeSelected);
                    intent.putExtra(MainActivity.PRIORITY_KEY,priority + " priority" );
                    intent.putExtra(MainActivity.TITLE_KEY,title);
                    intent.putExtra("radio", radioButtonGroup.getCheckedRadioButtonId());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }
}

























