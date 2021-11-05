package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HabitEventListActivity extends AppCompatActivity {
    ListView habitEventList;
    ArrayAdapter<HabitEvent> habitEventArrayAdapter;
    ArrayList<HabitEvent> habitEventDataList;
    private ImageView backImage;
    private ImageView addImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_list);
        habitEventList=(ListView) findViewById(R.id.habit_event_list);
        habitEventDataList=new ArrayList<>();
        HabitEvent habitEvent1 = new HabitEvent("Uof A" );
        //HabitEvent habitEvent2 = "2021-12-13";
        habitEventDataList.add(habitEvent1);
        //habitEventDataList.add(habitEvent2);
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        habitEventList.setAdapter(habitEventArrayAdapter);

        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HabitEventListActivity.this, HabitEventActivity.class);
                startActivity(intent);
            }
        });
        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addImage = findViewById(R.id.addImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitEventListActivity.this, HabitEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
