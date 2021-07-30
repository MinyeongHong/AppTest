package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Part1Prob extends AppCompatActivity {

    // voice recording
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private MediaRecorder recorder;
    private String outputFile;

    // playing voice
    private MediaPlayer player;
    private int position = 0;

    private ImageButton record;
    private Button stop, play, next;

    // countdown
    TextView textViewCountDown;
    private static final long COUNTDOWN_IN_MILLIS = 10000;
    private long timeLeftInMillis;
    CountDownTimer countDownTimer;

    // DB
    private TestDBHelper mTestDBHelper;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if(grantResults.length>0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(Part1Prob.this, "Audio 권한을 사용자가 승인함.", Toast.LENGTH_LONG).show();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(Part1Prob.this, "Audio 권한을 사용자가 거부함.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Part1Prob.this,"Audio 권한을 부여받지 못", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part1_prob);

        // initialize DB
        mTestDBHelper = new TestDBHelper(Part1Prob.this);

        int permissionCheck = ContextCompat.checkSelfPermission(Part1Prob.this, Manifest.permission.RECORD_AUDIO);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Part1Prob.this,"Audio 권한 있음.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(Part1Prob.this, "Audio 권한 없음.", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(Part1Prob.this, Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(Part1Prob.this, "Audio 권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(Part1Prob.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            }
        }

        record = (ImageButton)findViewById(R.id.record);
        stop = (Button)findViewById(R.id.btn_submit);
        play = (Button)findViewById(R.id.play);
        next = (Button)findViewById(R.id.btn_next);

        outputFile = getExternalCacheDir().getAbsolutePath();
        outputFile += "/audiorecordtest.3gp";

        record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recordAudio();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
                Toast.makeText(Part1Prob.this,"재생 시작", Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Part1Prob.this, Part2Prob.class);
                startActivity(intent);
            }
        });

        // for Countdown
        textViewCountDown = findViewById(R.id.tv_countdown);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

    public void recordAudio(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(outputFile);

        try{
            recorder.prepare();

            Toast.makeText(Part1Prob.this,"녹음 시작", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();
        }
        recorder.start();
    }

    public void stopRecording(){
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;

            Toast.makeText(Part1Prob.this,"녹음 중지", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAudio(){
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(outputFile);
            player.prepare();
            player.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closePlayer(){
        if(player !=null){
            player.release();
            player = null;
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                finish();
            }
        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);

        textViewCountDown.setText(timeFormatted);
    }


}
