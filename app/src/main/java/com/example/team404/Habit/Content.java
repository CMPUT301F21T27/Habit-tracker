package com.example.team404.Habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.team404.Habit.Habit;
import com.example.team404.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;

/**
 * This class is an adapter class
 * that associate with the habit_content xml
 * show what content of items in list will show
 */
public class Content extends ArrayAdapter<Habit> {
private ArrayList<Habit> habits;
private Context context;
DonutProgress donutProgress;


private ArrayList<String> habit_refers;



    /**
     * constructor
     * @param c
     * @param h
     */
    public Content(Context c, ArrayList<Habit> h){
    super(c,0,h);
    this.habits =h;
    this.context=c;






    }

    /**
     * Connects to the layout and format the habits as item in the list
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent){
    View view = convertView;
    if(view == null){
        view = LayoutInflater.from(context).inflate((R.layout.habit_content),parent,false);

    }


    Habit habit = habits.get(position);

    TextView habitTitle = view.findViewById(R.id.titleTextView);
    TextView habitReason = view.findViewById(R.id.reasonTextView);
    TextView habitDate = view.findViewById(R.id.dateTextView);

    donutProgress =(DonutProgress) view.findViewById(R.id.donut_progress);

/*
indicators show how close that user follow the plan
using total days that user follow the plan divide by total days of plan
will get the percentage. That will show how close that user follow the plan
 */
        System.out.println("result-------------"+habit.getTotal_habit_day());
        System.out.println("result-------------"+habit.getTotal_did());
        float result = (float) (habit.getTotal_did()*1.0/ habit.getTotal_habit_day());

    if(habit.getTotal_did()<=habit.getTotal_habit_day()){
        System.out.println("result-------------"+result);
        int result_int = (int) (result*100);
        System.out.println("result_int---"+result_int);
        donutProgress.setProgress(result_int);

    }


    habitTitle.setText(habit.getTitle());
    habitDate.setText(habit.getYear() + "-" +habit.getMonth()+ "-" +habit.getDay());
    habitReason.setText(habit.getReason());


    return view;

}
}
