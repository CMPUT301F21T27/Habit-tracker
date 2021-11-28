

package com.example.team404;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 =======
 package com.example.team404;

 import static android.content.ContentValues.TAG;
 import static java.lang.String.valueOf;

 import android.app.AlertDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.ListView;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;

 import com.google.android.gms.tasks.OnCompleteListener;
 import com.google.android.gms.tasks.Task;
 import com.google.android.material.bottomnavigation.BottomNavigationView;
 import com.google.android.material.floatingactionbutton.FloatingActionButton;
 import com.google.android.material.navigation.NavigationBarView;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.firestore.CollectionReference;
 import com.google.firebase.firestore.DocumentReference;
 import com.google.firebase.firestore.DocumentSnapshot;
 import com.google.firebase.firestore.FirebaseFirestore;
 import com.google.firebase.firestore.QueryDocumentSnapshot;
 import com.google.firebase.firestore.QuerySnapshot;

 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Collection;
 import java.util.Date;
 import java.util.List;

 /**
 >>>>>>> b4a88cddd663b33ca9d4eff51db9efd335942727
 * This activity is use to display user's Habits. That user can view, edit, delete habits through here. User can also
 * access the habits to do today through here
 */

public class MyActivity extends AppCompatActivity implements AddHabitFragment.OnFragmentInteractionListener {
    // declare layout variables
    ListView habitList;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;
    Button today;
    ImageView reorder;


    /**
     * The main process of MyActivity class.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        final FirebaseFirestore db;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        db=FirebaseFirestore.getInstance();
        DocumentReference userDoc = FirebaseFirestore.getInstance().collection("User").document(userEmail);

        habitDataList = new ArrayList<>();






        //initialize the layout variables and connect to the layouts

        setContentView(R.layout.activity_my);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_my);
        bottomNav.setOnItemSelectedListener(navListener);
        reorder=(ImageView)findViewById(R.id.reorder_button);
        habitList=(ListView) findViewById(R.id.habit_list);


        habitDataList=new ArrayList<>();
        habitArrayAdapter = new Content(this,habitDataList);

        db.collection("Habit")
                .whereEqualTo("OwnerReference",userDoc)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String id =  document.getData().get("id").toString();
                                String title = document.getData().get("Title").toString();
                                String reason = document.getData().get("Reason").toString();
                                String year = document.getData().get("Year").toString();
                                String month = document.getData().get("Month").toString();
                                String day = document.getData().get("Day").toString();
                                String pub = document.getData().get("Public").toString();
                                int total = Integer.valueOf((document.getData().get("Total").toString()));
                                String last = document.getData().get("Last").toString();
                                int total_did= Integer.valueOf((document.getData().get("Total Did").toString()));






                                Habit habit = new Habit(id,title,reason,year,month,day);

                                if (pub.contains("True")){
                                    habit.setPub(true);
                                }


                                String plan = document.getData().get("Plan").toString();
                                if (plan.contains("Monday")){
                                    habit.setMonday(true);
                                }
                                if (plan.contains("Tuesday")){
                                    habit.setTuesday(true);
                                }
                                if (plan.contains("Wednesday")){
                                    habit.setWednesday(true);
                                }
                                if(plan.contains("Thursday")){
                                    habit.setThursday(true);
                                }
                                if(plan.contains("Friday")){
                                    habit.setFriday(true);
                                }
                                if(plan.contains("Saturday")){
                                    habit.setSaturday(true);
                                }
                                if(plan.contains("Sunday")){
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

                                    total=0;


                                    LocalDate today = LocalDate.now();
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd");
                                    LocalDate startDate = LocalDate.of(Integer.valueOf(habit.getYear())
                                            ,Integer.valueOf(habit.getMonth()),Integer.valueOf(habit.getDay()));
                                    long noOfDaysBetween = ChronoUnit.DAYS.between(startDate, today);

                                        total= (int)(noOfDaysBetween)+1;
                                        int total_days=0;
                                        for(int i=0; i<total;i++){
                                            LocalDate everyDay =startDate.plusDays(i);
                                            if((everyDay.getDayOfWeek()== DayOfWeek.MONDAY)&&(habit.getMonday())){
                                                total_days++;
                                            }else if((everyDay.getDayOfWeek()== DayOfWeek.TUESDAY)&&(habit.getTuesday())){
                                                total_days++;
                                            }else if((everyDay.getDayOfWeek()== DayOfWeek.WEDNESDAY)&&(habit.getWednesday())){
                                                total_days++;
                                            }
                                            else if((everyDay.getDayOfWeek()== DayOfWeek.THURSDAY)&&(habit.getThursday())){
                                                total_days++;
                                            }
                                            else if((everyDay.getDayOfWeek()== DayOfWeek.FRIDAY)&&(habit.getFriday())){
                                                total_days++;
                                            }
                                            else if((everyDay.getDayOfWeek()== DayOfWeek.SATURDAY)&&(habit.getSaturday())){
                                                total_days++;
                                            }
                                            else if((everyDay.getDayOfWeek()== DayOfWeek.SUNDAY)&&(habit.getSunday())){
                                                total_days++;
                                            }


                                        }
                                        


                                    //http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date/Get_all_Monday_dates_in_given_month.htm







                                    last=formatter.format(date_now);
                                    Map<String,Object> h = new HashMap<>();
                                    h.put("Total",total_days);
                                    h.put("Last",last);
                                    db.collection("Habit").document(id)
                                            .update("Total",total,
                                                    "Last",last);



                                habit.setLastDay(last);
                                habit.setTotal_habit_day(total_days);
                                habit.setTotal_did(total_did);


                                habitDataList.add(habit);
                                habitList.setAdapter(habitArrayAdapter);



                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }

                });


        habitArrayAdapter = new Content(this,habitDataList);
        habitList.setAdapter(habitArrayAdapter);

        reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comparator<Habit> compareByTitle = new Comparator<Habit>() {
                    @Override
                    public int compare(Habit o1, Habit o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                };
                Collections.sort(habitDataList,compareByTitle);
                habitList.setAdapter(habitArrayAdapter);
            }
        });
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
        if (newHabit.getTitle().equals("") || newHabit.getReason().equals("") || newHabit.getDay().equals("0")) {
            Toast.makeText(MyActivity.this, "The title, reason and date cannot be empty", Toast.LENGTH_SHORT).show();


        } else {
            final FirebaseFirestore db;
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            String userEmail = user.getEmail();
            db = FirebaseFirestore.getInstance();
            DocumentReference userDoc = FirebaseFirestore.getInstance().collection("User").document(userEmail);
            String day = "";
            if (newHabit.getMonday()) {
                day = day + "Monday";
            }
            if (newHabit.getTuesday()) {
                day = day + "Tuesday";
            }
            if (newHabit.getWednesday()) {
                day = day + "Wednesday";
            }
            if (newHabit.getThursday()) {
                day = day + "Thursday";
            }
            if (newHabit.getFriday()) {
                day = day + "Friday";
            }
            if (newHabit.getSaturday()) {
                day = day + "Saturday";
            }
            if (newHabit.getSunday()) {
                day = day + "Sunday";
            }
            String pub = "";
            if (newHabit.getPub()) {
                pub = "True";
            } else pub = "False";


            /** if no previous habit selected, it will add a new habit onto the list**/
            if (habit == null) {
                Map<String, Object> h = new HashMap<>();
                h.put("Day", newHabit.getDay());
                h.put("Month", newHabit.getMonth());
                h.put("OwnerReference", userDoc);
                h.put("OwnerEmail", userEmail);
                h.put("Reason", newHabit.getReason());
                h.put("Title", newHabit.getTitle());
                h.put("Year", newHabit.getYear());
                h.put("id", newHabit.getId());
                h.put("Last", newHabit.getLastDay());
                h.put("Total", newHabit.getTotal_habit_day());
                h.put("Total Did", newHabit.getTotal_did());

                h.put("Public", pub);

                h.put("Plan", day);
                db.collection("Habit").document(newHabit.getId())
                        .set(h)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                habitArrayAdapter.add(newHabit);
            }
            /** otherwise, it will edit a existing habit**/
            else {

                db.collection("Habit").document(habit.getId())
                        .update("Day", newHabit.getDay(),
                                "Month", newHabit.getMonth(),
                                "Reason", newHabit.getReason(),
                                "Title", newHabit.getTitle(),
                                "Year", newHabit.getYear(),
                                "Public", pub,
                                "Plan", day);

                int index = habitDataList.indexOf(habit);
                habitDataList.get(index).setTitle(newHabit.getTitle());
                habitDataList.get(index).setMonday(newHabit.getMonday());
                habitDataList.get(index).setTuesday(newHabit.getTuesday());
                habitDataList.get(index).setWednesday(newHabit.getWednesday());
                habitDataList.get(index).setThursday(newHabit.getThursday());
                habitDataList.get(index).setFriday(newHabit.getFriday());
                habitDataList.get(index).setSaturday(newHabit.getSaturday());
                habitDataList.get(index).setSunday(newHabit.getSunday());
                habitDataList.get(index).setPub(newHabit.getPub());

                if ((Integer.valueOf(newHabit.getMonth()) > 0)) {
                    habitDataList.get(index).setYear(newHabit.getYear());
                    habitDataList.get(index).setMonth(newHabit.getMonth());
                    habitDataList.get(index).setDay(newHabit.getDay());
                } else {
                    habitDataList.get(index).setYear(habit.getYear());
                    habitDataList.get(index).setMonth(habit.getMonth());
                    habitDataList.get(index).setDay(habit.getDay());
                }
                habitDataList.get(index).setReason(newHabit.getReason());
            }


            habitList.setAdapter(habitArrayAdapter);
        }
    }
    /**
     * Delete a habit from the list by pressing the negative button on the dialog
     * @param habit
     */
    @Override
    public void OnDlPressed(Habit habit) {
        final FirebaseFirestore db;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        db=FirebaseFirestore.getInstance();
        DocumentReference userDoc = FirebaseFirestore.getInstance().collection("User").document(userEmail);
        db.collection("Habit").document(habit.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
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
                            return true;
                        //break;

                        case R.id.nav_subscribe:
                            intent = new Intent(getApplicationContext(), SubscribeActivity.class);
                            //startActivity(intent);
                            //overridePendingTransition(0, 0);
                            //return true;
                            break;
                    }
                    finish();
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
                            FirebaseAuth.getInstance().signOut();
                            finishAffinity();
                        }
                    }).create().show();
        }else{

        }

    }
    @Override
    public void onRestart(){
        super.onRestart();
        habitList.setAdapter(habitArrayAdapter);
    }


}