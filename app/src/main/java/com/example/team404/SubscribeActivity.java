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
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubscribeActivity extends AppCompatActivity {

    //private String followingListString;
    //private List<String> followingList;
    ListView habitList;
    ArrayAdapter<Habit> habitArrayAdapter;
    ArrayList<Habit> habitDataList;

    public SubscribeActivity(){
        // require a empty public constructor
    }

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
        setContentView(R.layout.activity_subscribe);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_subscribe);
        bottomNav.setOnItemSelectedListener(navListener);
        habitList=findViewById(R.id.subscribe_list);
        habitDataList=new ArrayList<>();
        habitArrayAdapter = new Content(this,habitDataList);



        userDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                List<String> following = (List<String>) doc.get("followingList");
                for (int a=0; a<following.size(); a++){
                    int currentA = a;
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
                                            if (email.equals(following.get(currentA))){
                                                habitDataList.add(habit);
                                                habitList.setAdapter(habitArrayAdapter);}


                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }

                                }

                            });
                }

            }
        });
        habitArrayAdapter = new Content(this,habitDataList);
        habitList.setAdapter(habitArrayAdapter);
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Habit habit_selected = (Habit) adapterView.getItemAtPosition(i);
                new ViewSubscribeList(habit_selected).show(getSupportFragmentManager(), "View_Habit");
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
                            return true;
                            //break;
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
                            //MainActivity.super.onBackPressed();
                            FirebaseAuth.getInstance().signOut();
                            finishAffinity();
                        }
                    }).create().show();
        }else{

        }

    }
}
