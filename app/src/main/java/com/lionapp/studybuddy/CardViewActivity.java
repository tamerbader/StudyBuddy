package com.lionapp.studybuddy;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class CardViewActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btnAdd;
    private Button btnDelete;
    public static Boolean didSelect = Task.didSelect;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    public int numTasks;
    public static Context appContext;
    private static int numberOfTasks;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private FileOutputStream fos;
    private FileInputStream fis;
    public List<Task> localStudentsList;
    public static List<Task> myTasks;
    public String jsonTasks;
    public static Task temporary = null;
    public static int removePosition = -1;
    public static int timeChange = -1;
    public static int timerChangeNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setting up the basics
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

// set an exit transition
            getWindow().setExitTransition(new Slide(Gravity.TOP));
        }
        setContentView(R.layout.activity_tasks);
        appContext = getApplicationContext();
        // End Basics


        // Shared Preferences
        prefs = this.getPreferences(Context.MODE_PRIVATE);  // Creating the SharedPreference and Editor.
        editor = prefs.edit();


        final Gson gson = new Gson(); // Setting up the Gson
        jsonTasks = prefs.getString("saveTask", null);  // Checks SharedPreferenc eto see if anything is there, i not return null
        if (jsonTasks == null) {
            myTasks = new ArrayList<Task>();
        } else {
            Type type  = new TypeToken<List<Task>>(){}.getType();
            myTasks = gson.fromJson(jsonTasks, type);
        }
        if (temporary != null) {
            myTasks.add(temporary);
            String jsonTask = gson.toJson(myTasks);
            editor.putString("saveTask", jsonTask);
            editor.commit();
            temporary = null;
        }
        if (removePosition != -1) {
            myTasks.remove(removePosition);
            String jsonTask = gson.toJson(myTasks);
            editor.putString("saveTask", jsonTask);
            editor.commit();
            removePosition = -1;
        }
        if(timeChange != -1) {
            if (myTasks.size() == 0) {

            } else {
                myTasks.get(timeChange).taskTime = timerChangeNumber;
                String jsonTask = gson.toJson(myTasks);
                editor.putString("saveTask", jsonTask);
                editor.commit();
                timeChange = -1;
            }
        }

        // End SharedPreference



        // Setting up the Buttons
        btnAdd = (Button) findViewById(R.id.btnShow);
        //btnDelete = (Button) findViewById(R.id.button);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Task temporary = new Task("HW", false);
                myTasks.add(temporary);
                String jsonTask = gson.toJson(myTasks);
                editor.putString("saveTask", jsonTask);
                editor.commit();
                startActivity(new Intent(CardViewActivity.this, CardViewActivity.class));*/
                launchActivity();
            }
        });
        /*btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastNumber = (myTasks.size());
                if (lastNumber != 0) {
                    int pos = (myTasks.size()) - 1;
                    myTasks.remove(pos);
                    String jsonTask = gson.toJson(myTasks);
                    editor.putString("saveTask", jsonTask);
                    editor.commit();
                    startActivity(new Intent(CardViewActivity.this, CardViewActivity.class));
                }


            }
        });*/



        // Setting up RecyclerView


            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // create an Object for Adapter
            mAdapter = new CardViewDataAdapter(myTasks, this);

            // set the adapter object to the Recyclerview
            mRecyclerView.setAdapter(mAdapter);

        // End RecyclerView



    }
    public static Context getContextOfApplication(){
        return appContext;
    }

    public static int getTaskNumber () {
        return numberOfTasks;
    }
    public void writeTaskNumber () {
        prefs = this.getPreferences(Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putInt("taskCounter", 7);
        editor.commit();
    }

    public void sampleReadSharedPreference(){
        Gson gson = new Gson();
        String jsonTasks = prefs.getString("saveTask", null);
        Type type  = new TypeToken<List<Task>>(){}.getType();
        myTasks = gson.fromJson(jsonTasks, type);
    }
    public void sampleWriteSharedPreference() {
        Gson gson = new Gson();
        myTasks = new ArrayList<Task>();
        myTasks.add(new Task("HW 1", false, 3600000));
        String jsonTasks = gson.toJson(myTasks);
        editor.putString("saveTask", jsonTasks);
        editor.commit();


    }
    public void addTask(Task p) {
        Gson gson = new Gson();
        myTasks.add(p);
        String jsonTask = gson.toJson(myTasks);
        editor.putString("saveTask", jsonTask);
        editor.commit();
        startActivity(new Intent(CardViewActivity.this, CardViewActivity.class));

    }
    // Beta fetaure of restricting access
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            startActivity(new Intent(CardViewActivity.this, MainScreen.class));
        }
        return super.onKeyDown(keyCode, event);
    }

// End of Beta Feature
public void launchActivity () {
    if (Build.VERSION.SDK_INT >= 21) {
        Intent intent = new Intent(CardViewActivity.this, NewTaskActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    } else {
        startActivity(new Intent(CardViewActivity.this, NewTaskActivity.class ));
    }

}











}
