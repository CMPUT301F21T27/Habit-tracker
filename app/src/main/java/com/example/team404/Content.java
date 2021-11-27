package com.example.team404;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an adapter class
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
