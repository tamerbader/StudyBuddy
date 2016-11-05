package com.lionapp.studybuddy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.sql.Time;

public class NewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public TextView taskName;
    public TextView hours;
    public TextView minutes;
    public TextView seconds;
    public TextView categoryLabel;
    public EditText taskSpace;
    public NumberPicker hourPicker;
    public NumberPicker minutePicker;
    public NumberPicker secondPicker;
    public Button btnSubmit;
    public Spinner categorySpinner;
    public String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Important Setting up stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        // End basic setup

        // Set up View Connections

        // Textview Setup
        taskName = (TextView) findViewById(R.id.taskName);
        hours = (TextView) findViewById(R.id.hours);
        minutes = (TextView) findViewById(R.id.minutes);
        seconds = (TextView) findViewById(R.id.seconds);
        categoryLabel = (TextView) findViewById(R.id.categoryLabel);
        Typeface avenir = Typeface.createFromAsset(getAssets(), "Avenir.otf");
        taskName.setTypeface(avenir);
        hours.setTypeface(avenir);
        minutes.setTypeface(avenir);
        seconds.setTypeface(avenir);
        categoryLabel.setTypeface(avenir);

        // EditText Set up
        taskSpace = (EditText) findViewById(R.id.taskSpace);
        taskSpace.setTypeface(avenir);

        // NumberPicker Set up
        hourPicker = (NumberPicker) findViewById(R.id.hourPicker);
        minutePicker = (NumberPicker) findViewById(R.id.minutePicker);
        secondPicker = (NumberPicker) findViewById(R.id.secondPicker);

        // Button Set up
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        // Spinner Set up
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);


        // End View Connection

        // Setup the Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskSpace.getText().toString();
                int hours = hourPicker.getValue();
                int minutes = minutePicker.getValue();
                int seconds = secondPicker.getValue();
                int totalMillisecondTime = (hours*3600000) + (minutes * 60000) + (seconds * 1000);
                System.out.println("SECONDS PICKED " + minutePicker.getValue());
                Task tempTask = new Task(taskName, false, totalMillisecondTime, categoryName);
                CardViewActivity.temporary = tempTask;
                finish();
                startActivity(new Intent(NewTaskActivity.this, CardViewActivity.class));
            }
        });






    }
    // Beta fetaure of restricting access
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            startActivity(new Intent(NewTaskActivity.this, CardViewActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Typeface avenir = Typeface.createFromAsset(getAssets(), "Avenir.otf");
        TextView myText = (TextView) view;
        categoryName = myText.getText().toString();
        myText.setTextColor(Color.parseColor("#ffffff"));
        myText.setTextSize(20);
        myText.setTypeface(avenir);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        categoryName = "";
        System.out.println("Hello");

    }
// End of Beta Feature
}
