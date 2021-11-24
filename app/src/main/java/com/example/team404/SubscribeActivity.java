package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SubscribeActivity extends AppCompatActivity {

    private Button HabitEventButton;
    public SubscribeActivity(){
        // require a empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_subscribe);
        bottomNav.setOnItemSelectedListener(navListener);



        //HabitEventButton = (Button) findViewById(R.id.habit_event_button);
        //HabitEventButton.setOnClickListener(v -> {
        //     Intent intent = new Intent(this, HabitEventActivity.class);
        //    startActivity(intent);
        //});
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
    @Override
    public void onBackPressed() {

    }
}
