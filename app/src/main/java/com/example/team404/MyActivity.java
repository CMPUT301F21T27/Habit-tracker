package com.example.team404;

import static java.lang.String.valueOf;

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
        /**prefill habit for testing */
        String year ="2021";
        String month ="11";
        String day = "05";
        Habit habit = new Habit("play the video game","Because no homework",year,month,day);


        habitDataList=new ArrayList<>();
        habitDataList.add(habit);

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

        Calendar c = Calendar.getInstance();
        String dayToday = valueOf(c.get(Calendar.DAY_OF_MONTH));
        String monthToday =valueOf(c.get(Calendar.MONTH+1));
        String yearToday = valueOf(c.get(Calendar.YEAR));


        Intent intent = new Intent (this,TodayActivity.class);
        ArrayList<Habit> habitsToday = new ArrayList<Habit>();
        for(Habit i :habitDataList){

            if ((habit.getDay().equals(dayToday))&&(habit.getMonth().equals(monthToday)&&(habit.getYear().equals(yearToday)))){
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
            /**Check when ok pressed
            if user did not edit date
            * we still use previous date
            * else we use new date */
            if((Integer.valueOf(newHabit.getMonth())>0)){
            habitDataList.get(index).setYear(newHabit.getYear());
            habitDataList.get(index).setMonth(newHabit.getMonth());
            habitDataList.get(index).setDay(newHabit.getDay());}

            else{
                habitDataList.get(index).setYear(habit.getYear());
                habitDataList.get(index).setMonth(habit.getMonth());
                habitDataList.get(index).setDay(habit.getDay());
            }
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
