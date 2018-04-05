/* Group 34
   Name: Akshay Karai, Naga Poorna Pujitha
   Homework assignment 2
* */

package edu.uncc.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    public static final int REQ_CODE = 100;

    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String PRIORITY_KEY = "Priority";
    public static final String TITLE_KEY = "Title";

    TextView taskNumber;
    TextView taskTotal;
    ImageView next;
    ImageView last;
    ImageView prev;
    ImageView first;
    int currentTask = 0;
    TextView titleText;
    TextView taskDateText;
    TextView taskTimeText;
    TextView taskPriorityText;

    LinkedList<Task> tasks = new LinkedList<Task>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        titleText = (TextView) findViewById(R.id.taskTitle);
        taskDateText = (TextView) findViewById(R.id.taskDate);
        taskTimeText = (TextView) findViewById(R.id.taskTime);
        taskPriorityText = (TextView) findViewById(R.id.taskPriority);

        ImageView delete = findViewById(R.id.deleteCurrentTask);
        ImageView edit = findViewById(R.id.editCurrentTask);



        ImageView button = findViewById(R.id.addTask);

        next = findViewById(R.id.goToNext);
        last = findViewById(R.id.goToLast);
        prev = findViewById(R.id.goToPrevious);
        first = findViewById(R.id.goToFirst);
        taskNumber = (TextView) findViewById(R.id.taskNumber);
        taskTotal = (TextView) findViewById(R.id.taskTotal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                i.putExtra("flag", "create");
                startActivityForResult(i, REQ_CODE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasks.size() > 1 && currentTask < tasks.size()-1) {
                    currentTask++;
                    titleText.setText(tasks.get(currentTask).getTitle());
                    taskDateText.setText(tasks.get(currentTask).getDate());
                    taskTimeText.setText(tasks.get(currentTask).getTime());
                    taskPriorityText.setText(tasks.get(currentTask).getPriority());

                    taskNumber.setText(currentTask+1 + "");
                }else{
                    Toast.makeText(MainActivity.this, "This is the last task.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks.size() > 1 && currentTask < tasks.size()-1){
                    currentTask = tasks.size()-1;
                    titleText.setText(tasks.get(currentTask).getTitle());
                    taskDateText.setText(tasks.get(currentTask).getDate());
                    taskTimeText.setText(tasks.get(currentTask).getTime());
                    taskPriorityText.setText(tasks.get(currentTask).getPriority());

                    taskNumber.setText(currentTask+1 + "");
                }else{
                    Toast.makeText(MainActivity.this, "This is the last task.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks.size()>1 && currentTask > 0){
                    currentTask--;
                    titleText.setText(tasks.get(currentTask).getTitle());
                    taskDateText.setText(tasks.get(currentTask).getDate());
                    taskTimeText.setText(tasks.get(currentTask).getTime());
                    taskPriorityText.setText(tasks.get(currentTask).getPriority());

                    taskNumber.setText(currentTask+1 + "");
                }else{
                    Toast.makeText(MainActivity.this, "This is the first task.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks.size()>1 && currentTask > 0){
                    currentTask = 0;
                    titleText.setText(tasks.get(currentTask).getTitle());
                    taskDateText.setText(tasks.get(currentTask).getDate());
                    taskTimeText.setText(tasks.get(currentTask).getTime());
                    taskPriorityText.setText(tasks.get(currentTask).getPriority());

                    taskNumber.setText(currentTask+1 + "");
                }else{
                    Toast.makeText(MainActivity.this, "This is the first task.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tasks.isEmpty()){
                    tasks.remove(currentTask);
                    taskTotal.setText(tasks.size() + "");

                    showFirstTask();
                }
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editTask = new Intent(MainActivity.this, CreateActivity.class);
                editTask.putExtra("flag", "edit");
                editTask.putExtra("task", tasks.get(currentTask));
                tasks.remove(currentTask);

                startActivityForResult(editTask, REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            String date = data.getExtras().getString(DATE_KEY);
            String time = data.getExtras().getString(TIME_KEY);
            String priority = data.getExtras().getString(PRIORITY_KEY);
            String title = data.getExtras().getString(TITLE_KEY);
            int radioId = data.getExtras().getInt("radio");

            Log.d("demo", date);
            Log.d("demo", time);
            Log.d("demo", priority);
            Log.d("demo", title);

            Task task = new Task();

            task.setPriority(priority);
            task.setTitle(title);
            task.setDate(date);
            task.setTime(time);
            task.setPriorityRadio(radioId);


            tasks.add(task);
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task task1, Task task2) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                    Date parsedDate1 = null;
                    Date parsedDate2 = null;
                    try {
                        parsedDate1 = formatter.parse(task1.getDate() + " " + task1.getTime());
                        parsedDate2 = formatter.parse(task2.getDate() + " " + task2.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (parsedDate1.getTime() > parsedDate2.getTime()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            if (!tasks.isEmpty()) {
                showFirstTask();
                /*titleText.setText(tasks.get(0).getTitle());
                taskDateText.setText(tasks.get(0).getDate());
                taskTimeText.setText(tasks.get(0).getTime());
                taskPriorityText.setText(tasks.get(0).getPriority());

                taskTotal.setText(tasks.size() + "");
                currentTask = 0;
                taskNumber.setText(currentTask+1 + "");*/
            }

        }
        } else if (resultCode == RESULT_CANCELED) {

            Log.d("demo", "cancelled");

        }

    }

    public void showFirstTask(){
        if(!tasks.isEmpty()){
            titleText.setText(tasks.get(0).getTitle());
            taskDateText.setText(tasks.get(0).getDate());
            taskTimeText.setText(tasks.get(0).getTime());
            taskPriorityText.setText(tasks.get(0).getPriority());

            taskTotal.setText(tasks.size() + "");
            currentTask = 0;
            taskNumber.setText(currentTask+1 + "");
        }else{
            titleText.setText("Task Title");
            taskDateText.setText("Task Date");
            taskTimeText.setText("Task Time");
            taskPriorityText.setText("Task Priority");
            currentTask = 0;
            taskNumber.setText(currentTask + "");
        }

    }

}

