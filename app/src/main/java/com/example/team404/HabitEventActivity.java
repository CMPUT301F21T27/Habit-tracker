package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HabitEventActivity extends AppCompatActivity {
    private Button LocationButton;
    private Button PhotoButton;
    private ImageView backImage;
    private TextView location;
    private TextView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_event_activity);

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LocationButton = (Button) findViewById(R.id.get_location_button);
        LocationButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);
        });
        PhotoButton = (Button) findViewById(R.id.get_photo_button);
        PhotoButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, PhotoActivity.class);
            startActivity(intent);
        });

    }


}
