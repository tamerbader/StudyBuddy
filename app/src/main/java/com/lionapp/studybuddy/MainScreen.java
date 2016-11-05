package com.lionapp.studybuddy;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainScreen extends Activity {
    TextView studyBuddy;
    TextView getWorkDone;
    ImageButton watch;
    TextView instruction;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_screen);
        mContext = this;

// set an exit transition
        Date newDate = new Date();
        long millis = newDate.getTime();
        System.out.println("Hello my name is " + millis);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = format.format(newDate);
        System.out.println("THE DATE IS " + date);
        studyBuddy = (TextView) findViewById(R.id.studyBuddy);
        getWorkDone = (TextView) findViewById(R.id.getWorkDone);
        watch = (ImageButton) findViewById(R.id.watch);
        instruction = (TextView) findViewById(R.id.instruction);

        Typeface arvo = Typeface.createFromAsset(getAssets(), "Arvo-Regular.ttf");
        studyBuddy.setTypeface(arvo);

        Typeface avenir = Typeface.createFromAsset(getAssets(), "Avenir.otf");
        getWorkDone.setTypeface(avenir);

        instruction.setTypeface(avenir);

        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(MainScreen.this, CardViewActivity.class));
            }
        });


        // Beta Features


    }
    public void launchActivity () {
        if (Build.VERSION.SDK_INT >= 21) {
            Intent intent = new Intent(MainScreen.this, CardViewActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(new Intent(MainScreen.this, CardViewActivity.class ));
        }

    }

}
