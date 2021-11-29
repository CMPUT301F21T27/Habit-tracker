package com.example.team404.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.team404.Habit.Habit;
import com.example.team404.HabitEvent.HabitEventListActivity;
import com.example.team404.R;

public class ViewTodayList extends DialogFragment {
    //--------------------------------
    //Open fragment to view detail of current habit
    //add habit event if it is valid
    //--------------------------------
    private TextView title;
    private TextView date_start;

    private TextView reason;

    private Habit habit_selected;

    private CheckBox mondayCheck;
    private CheckBox tuesdayCheck;
    private CheckBox wednesdayCheck;
    private CheckBox thursdayCheck;
    private CheckBox fridayCheck;
    private CheckBox saturdayCheck;
    private CheckBox sundayCheck;
    private Button addEventButton;
    private Button followButton;


    /**
     * constructors
     * @param habit_selected
     */
    public ViewTodayList(Habit habit_selected) {
        this.habit_selected = habit_selected;
    }
    @NonNull
    @Override
    /**
     * main progress when View Today List has been created
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_show_list, null);
        title = view.findViewById(R.id.title_main_Text);
        date_start = view.findViewById(R.id.date_main_Text);
        reason = view.findViewById(R.id.reason_main_Text);
        mondayCheck = view.findViewById(R.id.monday_check_main);
        tuesdayCheck = view.findViewById(R.id.tuesday_check_main);
        wednesdayCheck = view.findViewById(R.id.wednesday_check_main);
        thursdayCheck = view.findViewById(R.id.thursday_check_main);
        fridayCheck = view.findViewById(R.id.friday_check_main);
        saturdayCheck = view.findViewById(R.id.saturday_check_main);
        sundayCheck = view.findViewById(R.id.sunday_check_main);

        /*
        read the habit that user selected
         */
        title.setText(habit_selected.getTitle());
        date_start.setText(habit_selected.getYear() +"-"+habit_selected.getMonth()+"-"+habit_selected.getDay());
        reason.setText(habit_selected.getReason());
        mondayCheck.setChecked(habit_selected.getMonday());
        tuesdayCheck.setChecked(habit_selected.getTuesday());
        wednesdayCheck.setChecked(habit_selected.getWednesday());
        thursdayCheck.setChecked(habit_selected.getThursday());
        fridayCheck.setChecked(habit_selected.getFriday());
        saturdayCheck.setChecked(habit_selected.getSaturday());
        sundayCheck.setChecked(habit_selected.getSunday());





        followButton = view.findViewById(R.id.button);
        followButton.setVisibility(Button.GONE);
        addEventButton= view.findViewById(R.id.add_event_button);
        addEventButton.setVisibility(Button.VISIBLE);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            /*
            go to the habit event activity to let user to add a habit event
            to this habit. That means the user complete the plan for that day.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HabitEventListActivity.class);
                String current_habit_id= habit_selected.getId();
                intent.putExtra("current_habit_id", current_habit_id);
                String today = "today";
                intent.putExtra("today", today);

                startActivity(intent);

            }
        });

        /*
        cancel button to go back to the today list activity
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("View Habit")
                .setNegativeButton("Cancel", null)
                .create();

    }
}
