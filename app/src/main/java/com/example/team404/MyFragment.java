package com.example.team404;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class MyFragment extends Fragment implements AddHabitFragment.OnFragmentInteractionListener {
    ListView habitList;
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

        final FloatingActionButton addHabitButton = rootView.findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"111", Toast.LENGTH_SHORT).show();

                new AddHabitFragment().show(getActivity().getSupportFragmentManager(),"Add_Edit_Habit");
            }
        });

        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit_selected = (Habit) adapterView.getItemAtPosition(i);
                new AddHabitFragment(habit_selected).show(getActivity().getSupportFragmentManager(), "Add_Edit_Habit");
            }
        });



        return rootView;
    }


    @Override
    public void OnOKPressed(Habit newHabit, Habit habit) {
        if (habit == null) {
            habitArrayAdapter.add(newHabit);
        }
        else {
            int index = habitDataList.indexOf(habit);
            habitDataList.get(index).setTitle(newHabit.getTitle());
            habitDataList.get(index).setStartDate(newHabit.getStartDate());
            habitDataList.get(index).setReason(newHabit.getReason());
        }
        habitList.setAdapter(habitArrayAdapter);
    }
    @Override
    public void OnDlPressed(Habit habit) {
            habitDataList.remove(habit);
            habitList.setAdapter(habitArrayAdapter);
    }
}
