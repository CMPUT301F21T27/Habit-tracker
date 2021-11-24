package com.example.team404;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is use to set up the add, edit, and delete Habit from the list
 */
public class AddHabitFragment extends DialogFragment {
    /** declare variables **/
    private EditText title;
    private TextView date_start;
    private Button edit_date_start;
    private EditText reason;
    private OnFragmentInteractionListener listener;
    private Habit habit_selected;
    private DatePickerDialog.OnDateSetListener DateStartSetListener;
    private CheckBox mondayCheck;
    private CheckBox tuesdayCheck;
    private CheckBox wednesdayCheck;
    private CheckBox thursdayCheck;
    private CheckBox fridayCheck;
    private CheckBox saturdayCheck;
    private CheckBox sundayCheck;
    private  Button habitEventListButton;

    private Switch pubSwitch;

    private int year;
    private int month;
    private int day;

    /**
     * constructor
     */
    public AddHabitFragment() {
        habit_selected = null;
    }

    /**
     * constructor
     * @param habit_selected
     */
    public AddHabitFragment(Habit habit_selected) {
        this.habit_selected = habit_selected;
    }

    /**
     * Fragment Interaction Listener interface
     */

    public interface OnFragmentInteractionListener {
        void OnOKPressed(Habit newHabit, Habit habit);

        void OnDlPressed(Habit habit);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;

        }
        else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteraction Listener");
        }

    }

    /**
     * main process of the class, to create dialog for user to add,edit,delete habits.
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_habit, null);
        title = view.findViewById(R.id.title_editText);
        date_start = view.findViewById(R.id.date_Start_Text);
        //edit_date_start = view.findViewById(R.id.date_Start_edit_button);
        reason = view.findViewById(R.id.reason_editText);
        mondayCheck=view.findViewById(R.id.monday_check);
        tuesdayCheck=view.findViewById(R.id.tuesday_check);
        wednesdayCheck=view.findViewById(R.id.wednesday_check);
        thursdayCheck=view.findViewById(R.id.thursday_check);
        fridayCheck=view.findViewById(R.id.friday_check);
        saturdayCheck=view.findViewById(R.id.saturday_check);
        sundayCheck=view.findViewById(R.id.sunday_check);
        pubSwitch=view.findViewById(R.id.switch1);
        /** User is editing an exist habit, then the editText will show the information of the previous habit **/
        if (habit_selected != null) {
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
            pubSwitch.setChecked(habit_selected.getPub());
            }
            date_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateStartSetListener, year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }


            });
        /** using date picker to set up the Start date of the habit **/
            DateStartSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    year = y;
                    month = m+1;
                    day = d;
                    date_start.setText(year+"-"+month+"-"+day);

                }
            };
            habitEventListButton = view.findViewById(R.id.habit_event_list_button);
            habitEventListButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), HabitEventListActivity.class);
                String current_habit_id= habit_selected.getId();
                intent.putExtra("current_habit_id", current_habit_id);
                intent.putExtra("Today", "Not today");

                startActivity(intent);
            });
        /** set up the Positive, neutral, Negative Button of the dialog for add, cancel, delete **/
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            return builder
                    .setView(view)
                    .setTitle("Add Habit")
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (habit_selected != null) {
                                listener.OnDlPressed(habit_selected);
                            }
                        }
                    })
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Date date= new Date();
                            String habit_id = String.valueOf(date);
                            String habit_title = title.getText().toString();
                            String habit_year;
                            String habit_month;
                            String habit_day;
                            if (habit_selected!=null && (day==0|| day==00)){
                                habit_year=habit_selected.getYear();
                                habit_month=habit_selected.getMonth();
                                habit_day=habit_selected.getDay();
                            }else {
                                habit_year = Integer.toString(year);
                                habit_month = Integer.toString(month);
                                habit_day = Integer.toString(day);
                            }
                            String habit_reason = reason.getText().toString();
                            Habit habit = new Habit(habit_id, habit_title, habit_reason, habit_year,habit_month,habit_day);

                            habit.setMonday(mondayCheck.isChecked());
                            habit.setTuesday(tuesdayCheck.isChecked());
                            habit.setWednesday(wednesdayCheck.isChecked());
                            habit.setThursday(thursdayCheck.isChecked());
                            habit.setFriday(fridayCheck.isChecked());
                            habit.setSaturday(saturdayCheck.isChecked());
                            habit.setSunday(sundayCheck.isChecked());
                            habit.setPub(pubSwitch.isChecked());


                            listener.OnOKPressed(habit, habit_selected);

                        }
                    }).create();




    }
}
