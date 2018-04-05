package com.example.rubby.inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThreadActivity extends AppCompatActivity {

    private final int THREADS_KEY = 100, ADD_THREAD_KEY = 200;

    TextView username;
    EditText new_thread_name;
    ListView all_threads;
    Button signout, add_thread;
    Handler handler;
    TokenResponse tokenResponse;
    Threads threads;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        signout = findViewById(R.id.buttonSignout);
        add_thread = findViewById(R.id.buttonAddThread);
        username = findViewById(R.id.textViewUserName);
        new_thread_name = findViewById(R.id.editTextAddThread);
        all_threads = findViewById(R.id.listViewThreads);

      //TO set USERNAME
        Gson gson = new Gson();
        String string=getToken().toString();
      tokenResponse = gson.fromJson(string, TokenResponse.class);
        username.setText(tokenResponse.getUser_fname() +" "+tokenResponse.getUser_lname());
    Log.d("uname",string.toString());

      /*
        try {
              } catch (Exception e) {
            e.printStackTrace();
        }
*/
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreadActivity.this, MainActivity.class);
                cleartoken();
                startActivity(intent);
                finish();
            }
        });

        add_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new_thread_name.getText().toString().isEmpty()){
                    addThread(new_thread_name.getText().toString());
                    new_thread_name.setText("");
                }
            }
        });

        handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what == THREADS_KEY) {
                    threads = (Threads) msg.obj;
                    ArrayList<Threads.Thread> t = threads.threads;
                    ThreadsAdapter threadsAdapter = new ThreadsAdapter(ThreadActivity.this, R.layout.display_thread, t);
                    all_threads.setAdapter(threadsAdapter);
                    all_threads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ThreadActivity.this, ChatroomActivity.class);
                            intent.putExtra("thread", threads.threads.get(position));
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        getThreads();
    }

    public void getThreads(){
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "BEARER " + tokenResponse.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d("test", str);

                Gson gson = new Gson();

                Threads threads = gson.fromJson(str, Threads.class);

                Message msg = new Message();
                msg.what = THREADS_KEY;
                msg.obj = threads;

                ThreadActivity.this.handler.sendMessage(msg);
            }
        });
    }

    public void addThread(String thread_name){
        RequestBody formBody = new FormBody.Builder()
                .add("title", thread_name)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                .post(formBody)
                .header("Authorization", "BEARER " + tokenResponse.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getThreads();
            }
        });
    }

    public void cleartoken(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", null);
        editor.commit();
    }

    public String getToken(){
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }
}
