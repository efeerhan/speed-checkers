package com.crunglers.speedcheckers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    public int time = 5;
    public Board board;
    public TextView timer;
    public int mode;

    CountDownTimer cdtReceive = new CountDownTimer(100000, 1000) {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void onTick(long millisUntilFinished) {
            timer.setText("Receiving...");
            time--;
        }

        public void onFinish() {
            //getMove
            cdtMake.start();
        }
    };

    CountDownTimer cdtMake = new CountDownTimer(50000, 1000) {
        @SuppressLint("DefaultLocale")
        public void onTick(long millisUntilFinished) {
            timer.setText(String.format("seconds: %d", time));
            time--;
        }

        public void onFinish() {
            //sendMove
            cdtReceive.start();
        }
    };

    public void getMove(){

    }

    public void sendMove(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        board = new Board(this);
        timer = findViewById(R.id.timer);

        cdtMake.start();

    }
}