package com.example.team404;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PhotoActivity<requestCode> extends AppCompatActivity {

    private ImageView backImage;
    private ImageView photo_view;
    Button take_photo_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        photo_view = findViewById(R.id.photo_view);
        take_photo_button = findViewById(R.id.take_photo_button);
        if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PhotoActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },

                    123);
        }
        take_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 123);
            }
        });
        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "0000", Toast.LENGTH_SHORT);
        if (requestCode == 123) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            Toast.makeText(this, "1111", Toast.LENGTH_SHORT);
            photo_view.setImageBitmap(captureImage);
            Toast.makeText(this, "222", Toast.LENGTH_SHORT);
        }
    }
}
