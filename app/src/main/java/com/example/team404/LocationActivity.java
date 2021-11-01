package com.example.team404;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LocationActivity extends HabitEventActivity{
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
