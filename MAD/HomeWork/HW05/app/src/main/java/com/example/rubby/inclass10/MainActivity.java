package com.example.rubby.inclass10;

import android.app.ProgressDialog;
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
    String token;
    EditText email, password;
    Button login, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    performLogin(email.getText().toString(), password.getText().toString());
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(!getToken().equals("")){
            Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
            startActivity(intent);
            finish();
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
               // Log.d("test", str);
                Gson gson = new Gson();
                TokenResponse tokenResponse = gson.fromJson(str, TokenResponse.class);
                saveToken(str);

                if (tokenResponse.getStatus().equals("error")) {
                    Toast.makeText(MainActivity.this, "Login information incorrect", Toast.LENGTH_SHORT).show();
                } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(MainActivity.this,ThreadActivity.class);

                        startActivity(intent);
                    }
                });}
            }
        });
    }

    public void saveToken(String token){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken(){
        SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
      //  Log.d("sp",sharedPreferences.getString("token",""));
        return sharedPreferences.getString("token","");
    }
}
