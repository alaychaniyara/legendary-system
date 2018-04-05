package com.example.rubby.inclass04;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ExecutorService threadPool;
    Handler handler;
    AlertDialog.Builder alert;
    ProgressDialog progress;

    SeekBar pwCount, pwLength;
    Button generateThread, generateAsync;
    TextView myCount;
    TextView myLength;
    TextView result;

    public int count, length, countIndex;
    LinkedList<String> pws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadPool = Executors.newFixedThreadPool(2);

        alert = new AlertDialog.Builder(MainActivity.this);

        count = 1;
        length = 8;
        countIndex = 0;

        handler = new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message msg) {
                //generateThread.setText(msg.obj.toString());
                progress.incrementProgressBy(1);
                pws.add(msg.obj.toString());
                countIndex++;
                if(countIndex == count){
                    progress.dismiss();
                    alert.setTitle("Passwords")
                    .setItems(pws.toArray(new String[pws.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.setText(pws.get(which));
                        }
                    });
                    alert.show();
                }
                return false;
            }
        });

        generateThread = findViewById(R.id.threadButton);
        generateAsync = findViewById(R.id.asyncButton);

        pwCount = findViewById(R.id.seekBarPassword);
        pwLength = findViewById(R.id.seekBarLength);

        myCount = findViewById(R.id.textViewCountProgress);
        myLength = findViewById(R.id.textViewLengthProgress);

        result = findViewById(R.id.textViewResult);

        myCount.setText(String.valueOf(1));
        myLength.setText(String.valueOf(8));

        pwCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myCount.setText(String.valueOf(progress+1));
                count = progress+1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pwLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myLength.setText(String.valueOf(progress+8));
                length = progress+8;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generateThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(MainActivity.this);
                progress.setCancelable(false);
                progress.setMessage("Generating Passwords...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setProgress(0);
                progress.setMax(count);
                progress.show();
                pws = new LinkedList();
                countIndex = 0;
                for(int i = 0; i < count; i++){
                    threadPool.execute(new Util());
                }
            }
        });

        generateAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UtilAsync().execute("");
            }
        });
    }

    public class Util implements Runnable{
        private static final String _NUM = "1234567890";
        private static final String _UCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String _LCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String _SYMB = "!@#$%/.";
        private static final int PROGRESS_START = 0x01;
        private static final int PROGRESS_RUNNING = 0x02;
        private static final int PROGRESS_END = 0x03;

        public String getPassword(int length){
            Random rand = new Random();
            boolean num = true;
            boolean upper = true;
            boolean lower = true;
            boolean special = true;

            String CHAR_SET = "";
            CHAR_SET = CHAR_SET.concat(_NUM); //append numbers to character set
            CHAR_SET = CHAR_SET.concat(_UCASE); //append uppercase
            CHAR_SET = CHAR_SET.concat(_LCASE); //append lowercase
            CHAR_SET = CHAR_SET.concat(_SYMB); //append special characters

            StringBuffer randStr = new StringBuffer();
            for(int i=0; i<length; i++){ //using randomly generated size of password
                int randomIndex = getRandomIndex(CHAR_SET.length()); //get random index
                char ch = CHAR_SET.charAt(randomIndex); //select character at random index
                randStr.append(ch); //append selected character to password substring
            }
            return  randStr.toString(); //return password string
        }

        private int getRandomIndex(int len){
            Random rand = new Random();
            int randomInt = 0;
            for(int i=0; i<10000; i++){
                for(int j=0; j<1000;j++){
                    randomInt = rand.nextInt(len);
                }
            }
            return randomInt;
        }

        @Override
        public void run() {
            String currentPwd = getPassword(length);
            Message msg = new Message();
            msg.obj = currentPwd;
            if(countIndex == 0){
                msg.what = PROGRESS_START;
            }
            else if(countIndex == count-1){
                msg.what = PROGRESS_END;
            }
            else{
                msg.what = PROGRESS_RUNNING;
            }
            handler.sendMessage(msg);
        }
    }

    public class UtilAsync extends AsyncTask<String, String, Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            String currentPwd;
            for(int i = 0; i < count; i++){
                currentPwd = com.example.rubby.inclass04.Util.getPassword(length);
                publishProgress(currentPwd);
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(MainActivity.this);
            progress.setCancelable(false);
            progress.setMessage("Generating Passwords...");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMax(count);
            progress.setProgress(0);
            progress.show();
            pws = new LinkedList();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progress.dismiss();
            alert.setTitle("Passwords")
                    .setItems(pws.toArray(new String[pws.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.setText(pws.get(which));
                        }
                    });
            alert.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            progress.incrementProgressBy(1);
            pws.add(values[0]);
        }
    }
}
