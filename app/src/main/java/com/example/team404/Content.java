package com.example.team404;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Content extends ArrayAdapter<Habit> {
private ArrayList<Habit> habits;
private Context context;

public Content(Context c, ArrayList<Habit> h){
    super(c,0,h);
    this.habits =h;
    this.context=c;


}
public View getView(int position, View convertView, ViewGroup parent){
    View view = convertView;
    if(view == null){
        view = LayoutInflater.from(context).inflate((R.layout.habit_content),parent,false);

    }
    Habit habit = habits.get(position);

    TextView habitTitle = view.findViewById(R.id.titleTextView);
    TextView habitReason = view.findViewById(R.id.reasonTextView);
    TextView habitDate = view.findViewById(R.id.dateTextView);


    habitTitle.setText(habit.getTitle());
    SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy"));
    String date = formatter.format(habit.getStartDate());
    habitDate.setText(date);
    habitReason.setText(habit.getReason());


    return view;

}
}
