package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyFragment extends Fragment {
    ListView habitList ;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;
    Button today;
    public MyFragment(){
        // require a empty public constructor
        //habitList = User.habits;

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        habitList=(ListView)rootView.findViewById(R.id.habit_list);
        Date date = new Date(2021-1900,11-1,3);
        Habit habit = new Habit("play the video game","Because no homework",date);
        Date date2 = new Date(2022-1900,11-1,3);
        Habit habit1 = new Habit("play the game","Because no homework",date2);
        System.out.print(date);
        habitDataList=new ArrayList<>();
       habitDataList.add(habit);
        habitDataList.add(habit1);
       habitArrayAdapter = new Content(getActivity(),habitDataList);
       habitList.setAdapter(habitArrayAdapter);

       today=(Button)rootView.findViewById(R.id.today_button);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String today_date = sdf.format(c.getTime());


        Intent intent = new Intent (MyFragment.this.getActivity(),TodayActivity.class);
        ArrayList<Habit> habitsToday = new ArrayList<Habit>();
        for(Habit i :habitDataList){
            String date1 = sdf.format(i.getStartDate());
            if (date1.equals(today_date)){
                habitsToday.add(i);
            }
        }
        intent.putExtra("habitsToday",habitsToday);
       today.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {

                startActivity(intent);
           }
       });




        return rootView;  }

}
