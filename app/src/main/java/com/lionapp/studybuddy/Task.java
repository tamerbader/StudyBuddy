package com.lionapp.studybuddy;

/**
 * Created by tamerbader on 6/18/16.
 */
import android.content.Context;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private static final long serialVersionUID  = 435544555;

    private String taskName;
    public String category;
    public int taskTime;
    private boolean isSelected;
    public static boolean didSelect = false;
    public Date dateDue;
    public Task() {

    }

    public Task(String name) {

        this.taskName = name;

    }

    public Task(String name, boolean isSelected, int time) {

        this.taskName = name;
        this.isSelected = isSelected;
        this.taskTime = time;
    }
    public Task(String name, boolean isSelected, int time, String cat) {
        this.taskName = name;
        this.isSelected = isSelected;
        this.taskTime = time;
        this.category = cat;
    }


    public String getName() {
        return taskName;
    }

    public void setName(String name) {
        this.taskName = name;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public static void startAct () {

    }

}
