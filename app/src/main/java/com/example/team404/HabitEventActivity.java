package com.example.team404;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HabitEventActivity extends AppCompatActivity {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private Button LocationButton;
    private Button PhotoButton;
    private ImageView backImage;
    private ImageView LocationImage;
    private ImageView imageView;
    private ImageView editImage;

    private TextView commentTextView;
    private TextView locationTextView;
    int position;


    private TextView locationvIEW;
    private TextView photo;
    private static final String TAG = "HabitEventActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event);

        commentTextView= findViewById(R.id.comment_textView);
        locationTextView = findViewById(R.id.locationTextView);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        DocumentReference habitDoc = FirebaseFirestore.getInstance().collection("Habit").document(id);
        String date = extras.getString("date");
        String location = extras.getString("location");
        String comment = extras.getString("comment");
        commentTextView.setText(comment);
        locationTextView.setText(location);


        if(isServicesOK()){
            init();
        }

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String current_location= locationTextView.getText().toString();
                String current_comment= commentTextView.getText().toString();

                Bundle extras = new Bundle();
                extras.putString("editTitle",current_location);
                extras.putString("editId", id);
                extras.putString("editDate", date);
                extras.putString("editComment", current_comment);
                intent.putExtras(extras);
                setResult(111, intent);
               
                onBackPressed();
            }
        });
        //save habitevent after press save button
        editImage = findViewById(R.id.editImage);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do it later
                String comment = commentTextView.getText().toString();
                String  location = locationTextView.getText().toString();

                Intent intent = new Intent(HabitEventActivity.this, EditHabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", id);
                extras.putString("location", location);
                extras.putString("comment", comment);
                intent.putExtras(extras);
                startActivityForResult(intent, 333);
            }
        });


        // to call Camera to get a photo
        imageView = findViewById(R.id.imageView);




    }
    //https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
    // author: Suragch (answered) GabrielBB(edited)
    //date: 11-5-2021; 12-13-2021
    // protected void onActivityResult(int requestCode, int resultCode, Intent data) is created by Suragch
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 333) {
            // Get String data from Intent
            String locationString = data.getStringExtra("locationString");
            String commentString = data.getStringExtra("commentString");

            // Set text view with string
            TextView textView = (TextView) findViewById(R.id.locationTextView);
            textView.setText(locationString);
            TextView textView1 = (TextView) findViewById(R.id.comment_textView);
            textView1.setText(commentString);
        }
    }
    //initial location image button
    //make the location button is valid
    private void init(){
        LocationImage = findViewById(R.id.locationImage);



    }

    //https://www.youtube.com/watch?v=fPFr0So1LmI&list=PLgCYzUzKIBE-vInwQhGSdnbyJ62nixHCt&index=6
    //Author: CodingWithMitch
    //date: 2017-10-6
    //the follow 2 method is cited from CodingWithMitch
    //private void moveCamera
    //public boolean isServicesOK()

    //check permission is valid or gps is valid.
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;

    }




}
