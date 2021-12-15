package com.example.zenji;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 3600000;

    private TextView mTextViewCount;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonWave;
    private Button mButtonBell;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private MediaPlayer bellSoundMP;
    private MediaPlayer waveSoundMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bellSoundMP = MediaPlayer.create(this, R.raw.bellsound);
        waveSoundMP = MediaPlayer.create(this, R.raw.seawaves);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextViewCount =  findViewById(R.id.textView);
        mButtonStartPause = findViewById(R.id.buttonStartStop);
        mButtonReset = findViewById(R.id.buttonReset);
        mButtonWave = findViewById(R.id.buttonWave);
        mButtonBell = findViewById(R.id.buttonBell);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        mButtonWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveSoundMP.start();
            }
        });

        mButtonBell.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bellSoundMP.start();
            }

        });
        updateCountDownText();
        //bellSoundMP.start();
    }

    private void startTimer(){
        //final MediaPlayer bellSoundMP = MediaPlayer.create(this, R.raw.bellsound);
        //final MediaPlayer waveSoundMP = MediaPlayer.create(this, R.raw.seawaves);

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);

    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("start");
        mButtonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }


    private void updateCountDownText(){
//        final MediaPlayer bellSoundMP = MediaPlayer.create(this, R.raw.bellsound);
//        final MediaPlayer waveSoundMP = MediaPlayer.create(this, R.raw.seawaves);

        int minutes  = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted  = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCount.setText(timeLeftFormatted);
        if ((seconds == 0) && (minutes % 10 == 0) && ! bellSoundMP.isPlaying()) {
            bellSoundMP.start();
        }
        if ((seconds == 0) && (minutes % 10 != 0) && ! waveSoundMP.isPlaying()) {
            waveSoundMP.start();
        }
//        if (! waveSoundMP.isPlaying()) waveSoundMP.release();
//        if (! bellSoundMP.isPlaying()) bellSoundMP.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void buttonOnClick(View v){
        Button button = (Button) v;
        ((Button )v).setText("clicked");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
