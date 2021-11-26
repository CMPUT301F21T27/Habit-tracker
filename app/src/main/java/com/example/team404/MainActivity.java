package com.example.team404;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddHabitFragment.OnFragmentInteractionListener {
    ListView habitList;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        final FirebaseFirestore db;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        db=FirebaseFirestore.getInstance();
        DocumentReference userDoc = FirebaseFirestore.getInstance().collection("User").document(userEmail);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(navListener);
        habitList=findViewById(R.id.main_list);
        habitDataList=new ArrayList<>();
        habitArrayAdapter = new Content(this,habitDataList);
        db.collection("Habit")
                .whereEqualTo("Public","True")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {



                                    String id = document.getData().get("id").toString();
                                    String title = document.getData().get("Title").toString();
                                    String reason = document.getData().get("Reason").toString();
                                    String year = document.getData().get("Year").toString();
                                    String month = document.getData().get("Month").toString();
                                    String day = document.getData().get("Day").toString();
                                    String email = document.getData().get("OwnerEmail").toString();
                                    int total = Integer.valueOf(document.getData().get("Total").toString());
                                    int total_did = Integer.valueOf(document.getData().get("Total Did").toString());
                                    String last = document.getData().get("Last").toString();

                                    Habit habit = new Habit(id, title, reason, year, month, day);
                                    habit.setPub(true);

                                    String plan = document.getData().get("Plan").toString();
                                    if (plan.contains("Monday")) {
                                        habit.setMonday(true);
                                    }
                                    if (plan.contains("Tuesday")) {
                                        habit.setTuesday(true);
                                    }
                                    if (plan.contains("Wednesday")) {
                                        habit.setWednesday(true);
                                    }
                                    if (plan.contains("Thursday")) {
                                        habit.setThursday(true);
                                    }
                                    if (plan.contains("Friday")) {
                                        habit.setFriday(true);
                                    }
                                    if (plan.contains("Saturday")) {
                                        habit.setSaturday(true);
                                    }
                                    if (plan.contains("Sunday")) {
                                        habit.setSunday(true);
                                    }
                                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                                Date date_now = new Date(System.currentTimeMillis());
                                boolean is_habit_today= false;
                                Calendar c = Calendar.getInstance();
                                int day_habit = c.get(Calendar.DAY_OF_WEEK);
                                switch (day_habit) {
                                    case Calendar.SUNDAY:
                                        if (habit.getSunday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.MONDAY:
                                        if (habit.getMonday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.TUESDAY:
                                        if (habit.getTuesday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.WEDNESDAY:
                                        if (habit.getWednesday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.THURSDAY:
                                        if (habit.getThursday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.FRIDAY:
                                        if (habit.getFriday()) {
                                            is_habit_today= true;
                                        }
                                        break;
                                    case Calendar.SATURDAY:
                                        if (habit.getSaturday()) {
                                            is_habit_today= true;
                                        }
                                        break;

                                }
                                if (!last.equals(formatter.format(date_now))&&(is_habit_today==true)){

                                    total = total+1;
                                    last=formatter.format(date_now);
                                    Map<String,Object> h = new HashMap<>();
                                    h.put("Total",total);
                                    h.put("Last",last);
                                    db.collection("Habit").document(id)
                                            .update("Total",total,
                                                    "Last",last);
                                }
                                habit.setLastDay(last);
                                habit.setTotal_habit_day(total);
                                habit.setTotal_did(total_did);
                                    if (!email.equals(userEmail)){
                                    habitDataList.add(habit);
                                    habitList.setAdapter(habitArrayAdapter);}




                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }

                });


        habitArrayAdapter = new Content(this,habitDataList);
        habitList.setAdapter(habitArrayAdapter);
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit_selected = (Habit) adapterView.getItemAtPosition(i);
                new ViewMainList(habit_selected).show(getSupportFragmentManager(), "Add_Edit_Habit");
            }
        });






    }
    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Intent intent = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            intent = new Intent(getApplicationContext(), MainActivity.class);

                            //break;
                            return true;

                        case R.id.nav_account:
                            intent = new Intent(getApplicationContext(), AccountActivity.class);

                            break;
                        case R.id.nav_my:
                            intent = new Intent(getApplicationContext(), MyActivity.class);

                            break;

                        case R.id.nav_subscribe:
                            intent = new Intent(getApplicationContext(), SubscribeActivity.class);

                            break;
                    }
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
            };


    @Override
    public void OnOKPressed(Habit newHabit, Habit habit) {

    }

    @Override
    public void OnDlPressed(Habit habit) {

    }

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
                            //MainActivity.super.onBackPressed();
                            FirebaseAuth.getInstance().signOut();
                            finishAffinity();
                        }
                    }).create().show();
        }else{

        }

    }
}