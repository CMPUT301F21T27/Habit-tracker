package com.example.team404.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team404.Habit.Content;
import com.example.team404.Habit.Habit;
import com.example.team404.R;

import java.util.ArrayList;

/**
 * This class is use to show user that the habit has to do today
 */
public class TodayActivity extends AppCompatActivity {
    /** declare variables **/
    ListView today_habit;
    ImageView back;
    ArrayAdapter<Habit> habitArrayAdapter;

    /**
     * main process of this class, to show user the habits has to do today
     * @param savedInstanceState
     */
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        /** initialize variables**/
        setContentView(R.layout.activity_today);

        today_habit=(ListView) findViewById(R.id.today_habit_list);
        back= findViewById(R.id.backImage);
        /**get the habits arraylist from My Activity**/
        Intent intent = getIntent();
        ArrayList<Habit> habitsToday = new ArrayList<Habit>();
        habitsToday = (ArrayList<Habit>)getIntent().getSerializableExtra("habitsToday");
        habitArrayAdapter = new Content(this,habitsToday);
        today_habit.setAdapter(habitArrayAdapter);
        today_habit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit_selected = (Habit) adapterView.getItemAtPosition(i);
                new ViewTodayList(habit_selected).show(getSupportFragmentManager(), "View_Today_Habit");
            }
        });
        today_habit.setAdapter(habitArrayAdapter);
/*
 * after click the back button, it will take user back to the My Activity page
 */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public void onRestart(){
        super.onRestart();

        startActivity(getIntent());
        finish();
        overridePendingTransition(0, 0);
        today_habit.setAdapter(habitArrayAdapter);
    }
    
}
