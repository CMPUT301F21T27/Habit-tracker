package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TodayActivity extends AppCompatActivity {
    ListView today_habit;
    Button back;
    ArrayAdapter<Habit> habitArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        today_habit=(ListView) findViewById(R.id.today_habit_list);
        back=(Button)findViewById(R.id.back_button);
        Intent intent = getIntent();
        ArrayList<Habit> habitsToday = new ArrayList<Habit>();
        habitsToday = (ArrayList<Habit>)getIntent().getSerializableExtra("habitsToday");
        habitArrayAdapter = new Content(this,habitsToday);
        today_habit.setAdapter(habitArrayAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
