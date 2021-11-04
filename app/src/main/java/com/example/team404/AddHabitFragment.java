package com.example.team404;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHabitFragment extends DialogFragment {
    private EditText title;
    private TextView date_start;
    private Button edit_date_start;
    private EditText reason;
    private OnFragmentInteractionListener listener;
    private Habit habit_selected;
    private DatePickerDialog.OnDateSetListener DateStartSetListener;
    private int year;
    private int month;
    private int day;

    public AddHabitFragment() {
        habit_selected = null;
    }

    public AddHabitFragment(Habit habit_selected) {
        this.habit_selected = habit_selected;
    }

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_habit, null);
        title = view.findViewById(R.id.title_editText);
        date_start = view.findViewById(R.id.date_Start_Text);
        edit_date_start = view.findViewById(R.id.date_Start_edit_button);
        reason = view.findViewById(R.id.reason_editText);
        if (habit_selected != null) {
            title.setText(habit_selected.getTitle());
            SimpleDateFormat formatter = new SimpleDateFormat(("dd.MM.yyyy"));
            String date = formatter.format(habit_selected.getStartDate());
            date_start.setText(date);
            reason.setText(habit_selected.getReason()); }
            edit_date_start.setOnClickListener(new View.OnClickListener() {
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
            DateStartSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    year = y;
                    month = m;
                    day = d;
                    Date dateDate = new Date(year, month, day);
                    SimpleDateFormat formatter = new SimpleDateFormat(("dd.MM.yyyy"));
                    String date = formatter.format(dateDate);
                    date_start.setText(date);

                }
            };
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
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String habit_title = title.getText().toString();
                            Date habit_date = (Date) date_start.getText();
                            String habit_reason = reason.getText().toString();
                            listener.OnOKPressed(new Habit(habit_title, habit_reason, habit_date), habit_selected);
                        }
                    }).create();




    }
}
