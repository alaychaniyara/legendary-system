package com.example.rubby.inclass10;

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

public class SignUpActivity extends AppCompatActivity {

    EditText fname, lname, email, password, rpassword;
    Button cancel, tosignup;
    Handler handler;

    private final OkHttpClient client = new OkHttpClient();
    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = findViewById(R.id.signupFName);
        lname = findViewById(R.id.signupLName);
        email = findViewById(R.id.signupEmail);
        password = findViewById(R.id.signupPassword);
        rpassword = findViewById(R.id.signupRPassword);
        cancel = findViewById(R.id.buttonCancel);
        tosignup = findViewById(R.id.buttonToSignUp);

      /*  handler = new Handler(){
            public void handleMessage(Message msg){
                TokenResponse tr = (TokenResponse) msg.obj;
                if(tr.getStatus().equals("error")){
                    Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, ThreadActivity.class);
                    intent.putExtra("username", tr.getUser_fname() + " " + tr.getUser_lname());
                    startActivity(intent);
                }
            }
        };
*/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || rpassword.getText().toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(rpassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Password fields do not match", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().length() < 4){
                    Toast.makeText(SignUpActivity.this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    performSignup(email.getText().toString(), password.getText().toString(), fname.getText().toString(), lname.getText().toString());
                }
            }
        });
    }

    public void performSignup(String email, String password, String fname, String lname){
        RequestBody formBody = new FormBody.Builder()
                .add("email", email).add("password", password).add("fname", fname).add("lname", lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                .post(formBody)
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

                TokenResponse tokenResponse = gson.fromJson(str, TokenResponse.class);
                if(tokenResponse.getStatus().equals("error"))
                {
                    Toast.makeText(SignUpActivity.this, tokenResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    saveToken(str);
                    Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, ThreadActivity.class);
                    startActivity(intent);

                }/*
                Message msg = new Message();
                msg.what = 100;
                msg.obj = tokenResponse;

                SignUpActivity.this.handler.sendMessage(msg);*/
            }
        });
    }

    public void saveToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public void cleartoken(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", null);
        editor.commit();
    }

    public String getToken(){
       /// SharedPreferences sharedPreferences=getSharedPreferences("preference_file_key",Context.MODE_PRIVATE);
       // Log.d("sp",sharedPreferences.getString("token",""));
        return sharedPreferences.getString("token","");
    }
}
