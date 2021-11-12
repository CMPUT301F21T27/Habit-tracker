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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class AddHabitEventActivity extends AppCompatActivity implements AddCommentFragment.onFragmentInteractionListener, EditCommentFragment.onFragmentInteractionListener  {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private Button LocationButton;
    private Button PhotoButton;
    private ImageView backImage;
    private ImageView LocationImage;
    private ImageView imageView;
    private ImageView saveImage;
    private TextView commentTextView;
    private TextView locationTextView;

    ListView habitEventList;
    ArrayAdapter<HabitEvent> habitEventArrayAdapter;
    ArrayList<HabitEvent> habitEventDataList;
    int position;


    private TextView locationvIEW;
    private TextView photo;
    private static final String TAG = "HabitEventActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        //Bundle extras = getIntent().getExtras();
        //String location = extras.getString("location");
        //String comment = extras.getString("comment");

        commentTextView= findViewById(R.id.comment_textView);
        locationTextView = findViewById(R.id.location_textView);
        //commentTextView.setText(comment);
        //locationTextView.setText(location);


        commentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = commentTextView.getText().toString();
                if (info.length() ==0){
                    Toast.makeText(AddHabitEventActivity.this, "Please press add button to add comment.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    EditCommentFragment editCommentFragment = EditCommentFragment.newInstance(info);
                    editCommentFragment.show(getSupportFragmentManager(), "EDIT String");
                }

            }
        });

        commentTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(AddHabitEventActivity.this).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete the String?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                commentTextView.setText("");
                            }
                        })
                        .setNegativeButton("No" , null).show();

                return true;
            }
        });


        // press button to add String
        final ImageButton addCommentButton = findViewById(R.id.add_comment);
        addCommentButton.setOnClickListener(v -> {
            if (commentTextView.getText().toString().length() != 0){
                Toast.makeText(this, "Please long press to delete current one.", Toast.LENGTH_SHORT).show();

            }else if (commentTextView.getText().toString().length() == 0){
                new AddCommentFragment().show(getSupportFragmentManager(), "ADD String");
            }

        });

        if(isServicesOK()){
            init();
        }

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent();

                //Bundle extras = new Bundle();
                //extras.putString("locationString", location);

                //extras.putString("commentString", comment);
                //intent.putExtras(extras);
                //setResult(333, intent);
                onBackPressed();
            }
        });
        //save habitevent after press save button
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I will do it later to updated habit event list by habit id and habit event id
                Toast.makeText(AddHabitEventActivity.this, "do it later", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }
        });
        // to call Camera to get a photo
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 123);
        });
        if (ContextCompat.checkSelfPermission(AddHabitEventActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddHabitEventActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },

                    123);
        }



    }
    //initial location image button
    //make the location button is valid
    private void init(){
        LocationImage = findViewById(R.id.locationImage);
        LocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddHabitEventActivity.this, MapsActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

    }
    //Waiting for next activity to pass string back in to the current activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("keyName");

                //locationTextView = (TextView) findViewById(R.id.location_textView);
                locationTextView.setText(returnString);
            }
        }

        if (requestCode == 123) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
        }
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
    @Override
    public void onOkPressed(String newCity){
        TextView comment = findViewById(R.id.comment_textView);
        comment.setText(newCity);}

    @Override
    public void onEditOkPressed(String newCity){
        TextView comment = findViewById(R.id.comment_textView);
        comment.setText(newCity);
    }
    @Override
    public void onCancelPressed(){}




}