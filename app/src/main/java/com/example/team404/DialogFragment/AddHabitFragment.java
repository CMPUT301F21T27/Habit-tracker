package com.example.team404.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.team404.Habit.Habit;
import com.example.team404.HabitEvent.HabitEventListActivity;
import com.example.team404.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is use to set up the add, edit, and delete Habit from the list
 */
public class AddHabitFragment extends DialogFragment {
    /* declare variables **/
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
    private Button habitEventListButton;

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

    /**
     * associate the fragment to the Fragment Interaction Listener.
     * @param context
     */
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
        pubSwitch=view.findViewById(R.id.pub_switch);
        /* if its editing a habit, user will not allow to change the date**/


        if (habit_selected!=null) {
            date_start.setClickable(false);
        }
        /* User is editing an exist habit, then the editText will show the information of the previous habit **/
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
        /* set up the starting date by using DatePickerDialog**/
        date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* if its editing a habit, it will not allow to let user change
                 * date and it will give a message that date cannot  get edit
                 */
                if(habit_selected!=null){
                    Toast.makeText(getActivity(), "Date cannot get edit", Toast.LENGTH_SHORT).show();
                }
                /* if its adding a new habit, it will set up the start date by
                 * pick a date on the DatePicker dialog
                 */
                /* Reference
                 * Date picker dialog
                 * https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
                 * author: stackoverflow user: Android_coder
                 * date :2013-02-13
                 */
                else{
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateStartSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();}
            }



        });
        /* using date picker to set up the Start date of the habit **/
        DateStartSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                LocalDate currentDate = LocalDate.now();
                LocalDate setD = LocalDate.of(y,m+1,d);
                if(!setD.isBefore(currentDate)){
                    year = y;
                    month = m+1;
                    day = d;

                }
                else{
                    year =currentDate.getYear();
                    month =currentDate.getMonthValue();
                    day = currentDate.getDayOfMonth();
                    Toast.makeText(getActivity(), "Date cannot be past", Toast.LENGTH_SHORT).show();


                }
                date_start.setText(year+"-"+month+"-"+day);



            }
        };
        /* if its editing a habit, user can press the habit event button to
         * see the list of habit event
         */
        habitEventListButton = view.findViewById(R.id.habit_event_list_button);
        habitEventListButton.setVisibility(habit_selected!=null? ImageView.VISIBLE: ImageView.GONE );
        habitEventListButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HabitEventListActivity.class);
            String current_habit_id= habit_selected.getId();
            intent.putExtra("current_habit_id", current_habit_id);
            intent.putExtra("today", "Not today");

            startActivity(intent);
        });
        /* set up the Positive, neutral, Negative Button of the dialog for add, cancel, delete **/
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit Habit")
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /* delete a habit if it exist
                         *
                         */
                        if (habit_selected != null) {
                            listener.OnDlPressed(habit_selected);
                        }
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*
                         * by clicking confirm, it will add a new habit to
                         * the list, or it will edit a exist habit in the list.
                         */
                        Date date= new Date();
                        String habit_id = String.valueOf(date);
                        String habit_title = title.getText().toString();
                        String habit_year;
                        String habit_month;
                        String habit_day;
                        /* make sure when editing a habit, it will not
                         * automatically change the start date to 0-0-0
                         */
                        if (habit_selected!=null && (day==0|| day==00)){
                            habit_year=habit_selected.getYear();
                            habit_month=habit_selected.getMonth();
                            habit_day=habit_selected.getDay();
                        }else {
                            habit_year = Integer.toString(year);
                            habit_month = Integer.toString(month);
                            habit_day = Integer.toString(day);
                        }
                        /*
                         * gathering all the variables and create a habit
                         * that will add to the list
                         */
                        String habit_reason = reason.getText().toString();
                        Habit habit = new Habit(habit_id, habit_title, habit_reason, habit_year,habit_month,habit_day);
                        /*
                        check is the day passed one day
                         */
                        if(habit_selected ==null){
                            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                            Date date_now = new Date(System.currentTimeMillis());
                            habit.setLastDay(formatter.format(date_now));
                            habit.setTotal_did(0);
                        }
                        habit.setMonday(mondayCheck.isChecked());
                        habit.setTuesday(tuesdayCheck.isChecked());
                        habit.setWednesday(wednesdayCheck.isChecked());
                        habit.setThursday(thursdayCheck.isChecked());
                        habit.setFriday(fridayCheck.isChecked());
                        habit.setSaturday(saturdayCheck.isChecked());
                        habit.setSunday(sundayCheck.isChecked());
                        habit.setPub(pubSwitch.isChecked());


                        /*
                        add to the list
                         */
                        listener.OnOKPressed(habit, habit_selected);

                    }
                }).create();




    }
}
