package com.lionapp.studybuddy;

/**
 * Created by tamerbader on 6/18/16.
 */
import java.sql.Time;
import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {
    public boolean didSelect = false;
    private List<Task> stList;
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    public Context mContexts;

    public CardViewDataAdapter(List<Task> students, Context context) {
        this.stList = students;
        this.mContexts = context;

    }

    // Create new views
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
       /* Changed*/ View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.my_text_view, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Context tvNames = viewHolder.tvName.getContext();
        Typeface avenir = Typeface.createFromAsset(tvNames.getAssets(), "Avenir.otf");

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getName());
        if (stList.get(position).getName().length() > 15) {
            viewHolder.tvName.setTextSize(18);
        }
        int totalSeconds = stList.get(position).taskTime / 1000;
        int hours = totalSeconds/3600;
        int minutes = (totalSeconds - (hours*3600))/60;
        int seconds = (totalSeconds - (hours*3600) - (minutes * 60));
        viewHolder.time.setText("Time: " + hours + "Hr " + minutes + "Min " + seconds + "Sec");
        viewHolder.tvName.setTypeface(avenir);


        viewHolder.tvName.setTag(stList.get(position));
        viewHolder.btnStartTimer.setTag(stList.get(position));


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code to Navigate to Timer Screen
                Context mContext = v.getContext();

                CardViewActivity.removePosition = pos;

                didSelect = true;
                Intent i = new Intent(mContext, CardViewActivity.class);
                mContext.startActivity(i);
                // End Code to Navigate to Timer Screen


            }



        });


        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = v.getContext();
                ImageButton ib = (ImageButton) v;
                int x = stList.get(pos).taskTime;
                System.out.println("TIME IN ADAPTER IS " + x);
                TimerActivity.taskPosition = pos;
                TimerActivity.taskTimeAmount = x;
                TimerActivity.taskName = stList.get(pos).getName();
                Intent j = new Intent(mContext, TimerActivity.class);
                ((CardViewActivity) mContexts).finish();
                mContext.startActivity(j);
            }
        });
        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = v.getContext();
                TextView ib = (TextView) v;
                int x = stList.get(pos).taskTime;
                System.out.println("TIME IN ADAPTER IS " + x);
                TimerActivity.taskPosition = pos;
                TimerActivity.taskTimeAmount = x;
                TimerActivity.taskName = stList.get(pos).getName();
                Intent j = new Intent(mContext, TimerActivity.class);
                mContext.startActivity(j);


            }
        });
        viewHolder.startFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = v.getContext();
                FrameLayout ib = (FrameLayout) v;
                int x = stList.get(pos).taskTime;
                System.out.println("TIME IN ADAPTER IS " + x);
                TimerActivity.taskPosition = pos;
                TimerActivity.taskTimeAmount = x;
                TimerActivity.taskName = stList.get(pos).getName();
                Intent j = new Intent(mContext, TimerActivity.class);
                ((CardViewActivity) mContexts).finish();
                mContext.startActivity(j);

            }
        });
        viewHolder.deleteFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to Navigate to Timer Screen
                Context mContext = v.getContext();
                CardViewActivity.removePosition = pos;
                didSelect = true;
                Intent i = new Intent(mContext, CardViewActivity.class);
                mContext.startActivity(i);
                // End Code to Navigate to Timer Screen

            }
        });

        if (stList.get(pos).category.equals( "Homework")) {
            viewHolder.card.setCardBackgroundColor(Color.parseColor("#499cce"));
        } else if (stList.get(pos).category.equals( "Tests/Exams")) {
            viewHolder.card.setCardBackgroundColor(Color.parseColor("#c22e14"));
        } else if (stList.get(pos).category.equals("Projects")) {
            viewHolder.card.setCardBackgroundColor(Color.parseColor("#59ab6a"));
        } else {

        }

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        if(stList.isEmpty()) {
            return 0;
        } else {
            return stList.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView time;

        //public CheckBox chkSelected;
        public ImageButton btnStartTimer;
        public ImageButton btnDelete;
        public RelativeLayout rel;
        public CardView card;
        public FrameLayout startFrame;
        public FrameLayout deleteFrame;

        public Task singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.textView);
            time = (TextView) itemLayoutView.findViewById(R.id.textView2);
            btnStartTimer = (ImageButton) itemLayoutView.findViewById(R.id.imageButton);
            btnDelete = (ImageButton) itemLayoutView.findViewById(R.id.imageButton2);
            rel = (RelativeLayout) itemLayoutView.findViewById(R.id.rel);
            card = (CardView) itemLayoutView.findViewById(R.id.card);
            startFrame = (FrameLayout) itemLayoutView.findViewById(R.id.startFrame);
            deleteFrame = (FrameLayout) itemLayoutView.findViewById(R.id.deleteFrame);

        }

    }

    // method to access in activity after updating selection
    public List<Task> getStudentist() {
        return stList;
    }
}
