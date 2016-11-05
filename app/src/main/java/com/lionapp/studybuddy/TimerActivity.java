package com.lionapp.studybuddy;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.sql.Time;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends Activity {
    TextView timeLeft;
    TextView timer;
    ImageView yellowCircle;
    ImageButton takeBreak;
    CountDownTimer x;
    CountDownTimer y;
    DonutProgress myDonut;
    int timeLefter = 0;
    public static int taskTimeAmount;
    public static int taskPosition;
    public  NotificationCompat.Builder mBuilder;
    public static String taskName;
    public NotificationManager mNotifyMgr;
    public Context context;
    public int stoppedTimeLeft;
    public int clickCounter = 0;
    public int restarted = 0;
    public int realTimer;
    public int progress = 0;
    int factor = 100;
    double timersStart;
    public static int completed = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        System.out.println("AT TOP CLICK COUNTER IS " + clickCounter);

        /* Setting up the essentials*/
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timer);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        context = getApplicationContext();
        // End setting up Essentials



        /* Setting up the views and buttons*/
        System.out.println("TIME IN TIMERACTIVITY IS " + taskTimeAmount);
        timeLeft = (TextView) findViewById(R.id.timeLeft);
        timer = (TextView) findViewById(R.id.timer);
        //yellowCircle = (ImageView) findViewById(R.id.yellowCircle);
        takeBreak = (ImageButton) findViewById(R.id.takeBreak);
        myDonut = (DonutProgress) findViewById(R.id.donut_progress);
        myDonut.setTextColor(Color.parseColor("#071428"));
        myDonut.setFinishedStrokeColor(Color.parseColor("#f6eb34"));
        Typeface arvo = Typeface.createFromAsset(getAssets(), "Arvo-Regular.ttf");
        Typeface sofia = Typeface.createFromAsset(getAssets(), "Sofia.otf");
        timeLeft.setTypeface(sofia);
        Typeface avenir = Typeface.createFromAsset(getAssets(), "Avenir.otf");
        timer.setTypeface(avenir);
        final Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.playicon, null);
        final Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();

        // End setting up the views

        // Set up notifications
        mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(taskName)
                        .setContentText("Your Time is Up!")
                        .setColor(Color.parseColor("#fd0000"))
                        .setSmallIcon(R.drawable.alarm);
        Intent resultIntent = new Intent(this, TimerActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

// Because clicking the notification opens a new ("special") activity, there's
// no need to create an artificial back stack.
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        // End setting up Notifications





        /* Setting up the timer*/
       /* y = new CountDownTimer(1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress++;
                if (progress > 100) {
                    myDonut.setProgress(100);
                } else {
                    myDonut.setProgress(progress);
                }
                System.out.println(progress);

            }

            @Override
            public void onFinish() {
                myDonut.setProgress(100);

            }
        };
        y.start();*/
        x = new CountDownTimer(taskTimeAmount, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLefter = (int)millisUntilFinished;
                String hms = String.format("%02d:%02d:%02d", millisUntilFinished/(3600*1000),
                        millisUntilFinished/(60*1000) % 60,
                        millisUntilFinished/1000 % 60);
                timer.setText(hms);

                // Notification Update
                mBuilder.setContentText("Time left is " + hms);
                mBuilder.setContentIntent(resultPendingIntent);
                int mNotificationId = 001;
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                // End Notification

                // Circle Progress Bar
                double fractionTimer = (millisUntilFinished/timersStart);
                System.out.println("Z IS " + fractionTimer);
                double percentTimer = (fractionTimer *100);
                int integerPercentTimer = (int) percentTimer;
                int officialProgress = (100 - integerPercentTimer);
                System.out.println("XYZ " + (integerPercentTimer));
                if (officialProgress >= 98) {
                    myDonut.setProgress(100);
                } else {
                    myDonut.setProgress(officialProgress);
                }
                // End Progress Bar


            }

            @Override
            public void onFinish() {
                completed = 1;
                // Setting up Alert Box to show when Time is over
                new AlertDialog.Builder(TimerActivity.this)
                        .setTitle("Times Up!")
                        .setMessage("Do you want to make or do another Task?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                CardViewActivity.removePosition = taskPosition;
                                startActivity(new Intent(TimerActivity.this, NewTaskActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                CardViewActivity.removePosition = taskPosition;
                                startActivity(new Intent(TimerActivity.this, CardViewActivity.class));



                            }
                        })
                        .setIcon(R.drawable.alarm)
                        .show();

                // End Alert Box


                // Setting up the Phoen to vibrate 3 times
                for(int i = 0; i < 3; i++) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(750);
                    try{
                        wait(1450);
                    } catch (InterruptedException e) {
                        System.out.println("INTERRUPTED");
                    }
                }

                // End Vibrator


                // Setting up to change the notification status
                mBuilder.setContentText("Your Time is Up!");
                mBuilder.setContentIntent(resultPendingIntent);
                CardViewActivity.removePosition = taskPosition;
                int mNotificationId = 001;
// Gets an instance of the NotificationManager service
                 mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                // End Notification status change





            }
        };

        // End Countdown Setup

        // Start the Timer
        timersStart = (double)taskTimeAmount;
        x.start();
        // End Timer Start

        // Not sure what this is doing here?

        mBuilder.setContentText("Time left is " );
               mBuilder.setContentIntent(resultPendingIntent);
                int mNotificationId = 001;
// Gets an instance of the NotificationManager service

// Builds the notification and issues it.
                mNotifyMgr.notify(mNotificationId, mBuilder.build());


        // The Take a Break Button Setup
        takeBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK COUNTER IS AT " + clickCounter);
                if (clickCounter == 0) {
                    takeBreak.setImageResource(R.drawable.resume_button);
                    CardViewActivity.timeChange = taskPosition;
                    CardViewActivity.timerChangeNumber = timeLefter;
                    x.cancel();
                    clickCounter ++;

                } else {
                    // New Countdown





                    takeBreak.setImageResource(R.drawable.take_break_button);
                    x = new CountDownTimer(timeLefter, 500) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeLefter = (int)millisUntilFinished;
                            String hms = String.format("%02d:%02d:%02d", millisUntilFinished/(3600*1000),
                                    millisUntilFinished/(60*1000) % 60,
                                    millisUntilFinished/1000 % 60);
                            timer.setText(hms);
                            mBuilder.setContentText("Time left is " + hms);
                            mBuilder.setContentIntent(resultPendingIntent);
                            int mNotificationId = 001;
// Gets an instance of the NotificationManager service
// Builds the notification and issues it.
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                            // Circle Progress Bar
                            double fractionTimer = (millisUntilFinished/timersStart);
                            System.out.println("Z IS " + fractionTimer);
                            double percentTimer = (fractionTimer *100);
                            int integerPercentTimer = (int) percentTimer;
                            int officialProgress = (100 - integerPercentTimer);
                            System.out.println("XYZ " + (integerPercentTimer));
                            if (officialProgress >= 98) {
                                myDonut.setProgress(100);
                            } else {
                                myDonut.setProgress(officialProgress);
                            }
                            // End Progress Bar


                        }

                        @Override
                        public void onFinish() {
                            completed = 1;
                            // Setting up Alert Box to show when Time is over
                            new AlertDialog.Builder(TimerActivity.this)
                                    .setTitle("Times Up!")
                                    .setMessage("Do you want to make or do another Task?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            CardViewActivity.removePosition = taskPosition;
                                            startActivity(new Intent(TimerActivity.this, NewTaskActivity.class));
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            CardViewActivity.removePosition = taskPosition;
                                            startActivity(new Intent(TimerActivity.this, CardViewActivity.class));
                                        }
                                    })
                                    .setIcon(R.drawable.alarm)
                                    .show();

                            // End Alert Box


                            // Setting up the Phoen to vibrate 3 times
                            for(int i = 0; i < 3; i++) {
                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                v.vibrate(750);
                                try{
                                    wait(1450);
                                } catch (InterruptedException e) {
                                    System.out.println("INTERRUPTED");
                                }
                            }

                            // End Vibrator


                            // Setting up to change the notification status
                            mBuilder.setContentText("Your Time is Up!");
                            mBuilder.setContentIntent(resultPendingIntent);
                            CardViewActivity.removePosition = taskPosition;
                            int mNotificationId = 001;
// Gets an instance of the NotificationManager service
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                            // End Notification status change




                        }
                    };
                    y = new CountDownTimer(taskTimeAmount, factor) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            progress++;
                            myDonut.setProgress(progress);
                            if (progress >= 99) {
                                myDonut.setProgress(100);
                            }
                        }

                        @Override
                        public void onFinish() {
                            myDonut.setProgress(100);

                        }
                    };

                    // End Countdown Setup

                    // Start the Timer
                   // y.start();
                    x.start();
                    clickCounter = 0;

                   // Intent mIntent = getIntent();
                    //finish();
                    //startActivity(mIntent);
                }

             //   finish();
               // startActivity(new Intent(TimerActivity.this, CardViewActivity.class));

            }
        });

        // End of Uncertainty




    }

    // Beta fetaure of restricting access
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            x.cancel();
            startActivity(new Intent(TimerActivity.this, CardViewActivity.class));


            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (completed == 1) {
                finish();
                CardViewActivity.removePosition = taskPosition;
                startActivity(new Intent(TimerActivity.this, CardViewActivity.class));
                completed = 0;
            } else {
                new AlertDialog.Builder(TimerActivity.this)
                        .setTitle(taskName)
                        .setMessage("Are you sure you want to stop working on " + taskName)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                CardViewActivity.timeChange = taskPosition;
                                CardViewActivity.timerChangeNumber = timeLefter;
                                x.cancel();
                                finish();
                                startActivity(new Intent(TimerActivity.this, CardViewActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.alarm)
                        .show();
            }



        }
        return super.onKeyDown(keyCode, event);
    }
    // End of Beta Feature
}
