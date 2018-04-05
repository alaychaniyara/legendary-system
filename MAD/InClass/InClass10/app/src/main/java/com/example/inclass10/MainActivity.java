package com.example.inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    String token,getemail,getpassword;
    public static int YOUR_INT_MESSAGE = 100;
    EditText email,password;
    Button login,signup;
    public Handler handler;
    TokenResponse tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        signup=findViewById(R.id.buttonSignUp);
        login=findViewById(R.id.buttonLogin);
        getemail=email.getText().toString();
        getpassword=password.getText().toString();

        if(getToken().isEmpty()) {


            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    tr = (TokenResponse) msg.obj;
                    if (tr.status.equals("error")) {
                        Toast.makeText(MainActivity.this, "Incorrect email and/or password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent=new Intent(MainActivity.this,ThreadActivity.class);

                        startActivity(intent);
                    }

                }


            };


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        performLogin(getemail, getpassword);
                    }
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            Log.d("TOKEN","Token Already Present");
            Intent intent=new Intent(MainActivity.this,ThreadActivity.class);
            startActivity(intent);
        }
    }

    public void performLogin(String email, String password) {

        RequestBody formBody = new FormBody.Builder()
                .add("email", email).add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test", "ya fucked up");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d("test", str);

                saveToken(str);
                Gson gson = new Gson();

                TokenResponse tokenResponse = gson.fromJson(str, TokenResponse.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(MainActivity.this,ThreadActivity.class);

                        startActivity(intent);
                    }
                });
                /*
                Message msg = new Message();
                msg.what = YOUR_INT_MESSAGE;
                msg.obj = tokenResponse;
                MainActivity.this.handler.sendMessage(msg);*/
              //  token = tokenResponse.getToken();
              //  Log.d("test", token);
            }
        });
    }


    public void saveToken(String token){
        SharedPreferences sharedPreferences= getSharedPreferences
                (getString(R.string.preferences_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken(){
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preferences_file_key),Context.MODE_PRIVATE);
        return sharedPreferences.getString("token","");
    }
}
