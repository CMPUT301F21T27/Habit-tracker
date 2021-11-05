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
/**
 * This activity is use to display user's Habits. That user can view, edit, delete habits through here. User can also
 * access the habits to do today through here
 */

public class MyActivity extends AppCompatActivity implements AddHabitFragment.OnFragmentInteractionListener {
    // declare layout variables
    ListView habitList;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;
    Button today;

    /**
     * The main process of MyActivity class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        //initialize the layout variables and connect to the layouts

        setContentView(R.layout.activity_my);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_my);
        bottomNav.setOnItemSelectedListener(navListener);


        habitList=(ListView) findViewById(R.id.habit_list);
        /**prefill habit for testing */
       // String year ="2021";
       // String month ="11";
        //String day1 = "05";
        //Habit habit = new Habit("play the video game","Because no homework",year,month,day1);


        habitDataList=new ArrayList<>();
        //habitDataList.add(habit);
        /** using Content class as adpter**/
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
    /** get current date by using calender **/
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

    /** go to Today Activity class (showing up the habits to do today) by using intent **/
        Intent intent = new Intent (this,TodayActivity.class);

       today.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               ArrayList<Habit> habitsToday = new ArrayList<Habit>();
               for(Habit i :habitDataList){
                    if (!(habitDataList.contains(i.getTitle()))) {
                        switch (day) {
                            case Calendar.SUNDAY:
                                if (i.getSunday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.MONDAY:
                                if (i.getMonday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.TUESDAY:
                                if (i.getTuesday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.WEDNESDAY:
                                if (i.getWednesday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.THURSDAY:
                                if (i.getThursday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.FRIDAY:
                                if (i.getFriday()) {
                                    habitsToday.add(i);
                                }
                                break;
                            case Calendar.SATURDAY:
                                if (i.getSaturday()) {
                                    habitsToday.add(i);
                                }
                                break;

                        }
                    }
               }
               intent.putExtra("habitsToday",habitsToday);
                startActivity(intent);
           }
       });




    }


    /**
     * After pressing positive button on the dialog. Which will add a habit on to the fragment
     * Or Editing one exist habit
     * @param newHabit
     * @param habit
     */
    @Override
    public void OnOKPressed(Habit newHabit, Habit habit) {
        /** if no previous habit selected, it will add a new habit onto the list**/
        if (habit == null) {
            habitArrayAdapter.add(newHabit);
        }
        /** otherwise, it will edit a existing habit**/
        else {
            int index = habitDataList.indexOf(habit);
            habitDataList.get(index).setTitle(newHabit.getTitle());
            habitDataList.get(index).setMonday(newHabit.getMonday());
            habitDataList.get(index).setTuesday(newHabit.getTuesday());
            habitDataList.get(index).setWednesday(newHabit.getWednesday());
            habitDataList.get(index).setThursday(newHabit.getThursday());
            habitDataList.get(index).setFriday(newHabit.getFriday());
            habitDataList.get(index).setSaturday(newHabit.getSaturday());
            habitDataList.get(index).setSunday(newHabit.getSunday());

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

    /**
     * Delete a habit from the list by pressing the negative button on the dialog
     * @param habit
     */
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