package com.example.team404;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class HabitEventContent extends ArrayAdapter<HabitEvent> {
    private ArrayList<HabitEvent> habitEevents;
    private Context context;



    public HabitEventContent(@NonNull Context c, ArrayList<HabitEvent> h) {
        super(c,0,h);
        this.habitEevents =h;
        this.context=c;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate((R.layout.habit_event_content),parent,false);

        }
        HabitEvent habitEvent = habitEevents.get(position);

        TextView habitEventTitle = view.findViewById(R.id.habitEventLocation);
        TextView habitEventDate = view.findViewById(R.id.data_textView);

        habitEventTitle.setText(habitEvent.getLocation());
        habitEventDate.setText(habitEvent.getDate());


        return view;

    }
}
