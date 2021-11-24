package com.example.team404;

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

public class ViewMainListt extends DialogFragment {
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




    public ViewMainListt(Habit habit_selected) {
        this.habit_selected = habit_selected;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_show_listt, null);
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
        addEventButton= view.findViewById(R.id.add_event_button);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HabitEventListActivity.class);
                String current_habit_id= habit_selected.getId();
                intent.putExtra("current_habit_id", current_habit_id);
                intent.putExtra("Today", "today");
                startActivity(intent);

            }
        });






        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("View Habit")
                .setNegativeButton("Cancel", null)
                .create();

    }
}
