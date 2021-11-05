package com.example.team404;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyActivity extends AppCompatActivity implements AddHabitFragment.OnFragmentInteractionListener {
    ListView habitList;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;
    Button today;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_my);
        bottomNav.setOnItemSelectedListener(navListener);


        habitList=(ListView) findViewById(R.id.habit_list);

        Date date = new Date(2021-1900,11-1,3);
        Habit habit = new Habit("play the video game","Because no homework",date);
        Date date2 = new Date(2022-1900,11-1,3);
        Habit habit1 = new Habit("play the game","Because no homework",date2);
        System.out.print(date);
        habitDataList=new ArrayList<>();
        habitDataList.add(habit);
        habitDataList.add(habit1);
        habitArrayAdapter = new Content(this,habitDataList);
        habitList.setAdapter(habitArrayAdapter);

        final FloatingActionButton addHabitButton = findViewById(R.id.add_habit_button);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddHabitFragment().show(getSupportFragmentManager(),"Add_Edit_Habit");
            }
        });

        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit_selected = (Habit) adapterView.getItemAtPosition(i);
                new AddHabitFragment(habit_selected).show(getSupportFragmentManager(), "Add_Edit_Habit");
            }
        });




       today=(Button) findViewById(R.id.today_button);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String today_date = sdf.format(c.getTime());


        Intent intent = new Intent (this,TodayActivity.class);
        ArrayList<Habit> habitsToday = new ArrayList<Habit>();
        for(Habit i :habitDataList){
            String date1 = sdf.format(i.getStartDate());
            if (date1.equals(today_date)){
                habitsToday.add(i);
            }
        }
        intent.putExtra("habitsToday",habitsToday);
       today.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {

                startActivity(intent);
           }
       });




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
    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            //startActivity(intent);
                            //overridePendingTransition(0, 0);
                            //return true;
                            break;

                        case R.id.nav_account:
                            intent = new Intent(getApplicationContext(), AccountActivity.class);
                            //startActivity(intent);
                            //overridePendingTransition(0, 0);
                            //return true;
                            break;
                        case R.id.nav_my:
                            intent = new Intent(getApplicationContext(), MyActivity.class);
                            //startActivity(intent);
                            //overridePendingTransition(0, 0);
                            //return true;
                            break;

                        case R.id.nav_subscribe:
                            intent = new Intent(getApplicationContext(), SubscribeActivity.class);
                            //startActivity(intent);
                            //overridePendingTransition(0, 0);
                            //return true;
                            break;
                    }
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
            };

    private int count = 0;
    @Override
    public void onBackPressed() {
        count++;
        if (count >1){
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MyActivity.super.onBackPressed();
                            finishAffinity();
                        }
                    }).create().show();
        }else{

        }

    }

}