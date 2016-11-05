package com.lionapp.studybuddy;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.lzyzsd.circleprogress.DonutProgress;

public class Progress extends AppCompatActivity {
    DonutProgress myDonut;
    public int percentageDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        myDonut = (DonutProgress) findViewById(R.id.donut_progress1);
        percentageDone = 0;
        myDonut.setRotation(270);
        CountDownTimer x = new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                percentageDone ++;
                myDonut.setProgress(percentageDone);

            }

            @Override
            public void onFinish() {
                System.out.println("HEY!");

            }
        };
        x.start();
    }
}
