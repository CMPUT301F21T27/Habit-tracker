package com.example.team404;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

public class MyFragment extends Fragment {
    ListView habitList ;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;
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
        Date date = new Date(2021,11,1);
        Habit habit = new Habit("play game","Because no homework",date);
        habitDataList=new ArrayList<>();
       habitDataList.add(habit);
       habitArrayAdapter = new Content(this.getContext(),habitDataList);
       habitList.setAdapter(habitArrayAdapter);


         


        return rootView;  }
}
