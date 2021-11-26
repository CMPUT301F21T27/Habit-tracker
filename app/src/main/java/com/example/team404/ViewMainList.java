package com.example.team404;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;

public class ViewMainList extends DialogFragment {
    private TextView title;
    private TextView date_start;

    private TextView reason;
    private TextView ownerEmail;

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
    private FirebaseFirestore db;
    private DocumentReference habitDocRef;
    private DocumentReference habitOwnerDocRef;
    private DocumentReference userDocRef;
    private String habitOwnerEmail;
    private String test;


    public ViewMainList(Habit habit_selected) {
        this.habit_selected = habit_selected;
    }
    @NonNull
    @Override
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
        ownerEmail = view.findViewById(R.id.ownerEmail);



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

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        String habitId = habit_selected.getId();
        db= FirebaseFirestore.getInstance();
        userDocRef = db.collection("User").document(userEmail);
        habitDocRef = db.collection("Habit").document(habitId);

        habitDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    habitOwnerEmail = value.getData().get("OwnerEmail").toString();
                    if (habitOwnerEmail.equals(userEmail)){
                        followButton = view.findViewById(R.id.button);
                        followButton.setVisibility(Button.GONE);
                        ownerEmail.setText("Cannot follow yourself!");
                        ownerEmail.setVisibility(TextView.VISIBLE);
                        return;
                    } else{
                        followButton = view.findViewById(R.id.button);
                        followButton.setVisibility(Button.VISIBLE);
                        followButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                habitOwnerDocRef = db.collection("User").document(habitOwnerEmail);
                                habitOwnerDocRef.update("requestedList", FieldValue.arrayUnion(userEmail));
                                ownerEmail.setText("Request sent to: "+habitOwnerEmail);
                                ownerEmail.setVisibility(v.VISIBLE);
                            }
                        });
                        }

                }
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
