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

public class HabitEventActivity extends AppCompatActivity implements AddCommentFragment.onFragmentInteractionListener, EditCommentFragment.onFragmentInteractionListener  {
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private Button LocationButton;
    private Button PhotoButton;
    private ImageView backImage;
    private ImageView LocationImage;
    private ImageView imageView;
    private ImageView saveImage;
    ListView commentList;
    ArrayAdapter<Comment> commentAdapter;
    ArrayList<Comment> commentDataList;
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

        commentList = findViewById(R.id.comment_list);


        String []accounts = {"Nice", "Good habit event", "I pick you"};

        commentDataList = new ArrayList<>();
        for (int i=0; i<accounts.length;i++){
            commentDataList.add(new Comment(accounts[i]));
        }
        commentAdapter = new CommentList(this, commentDataList);
        commentList.setAdapter(commentAdapter);
        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;

                Comment list_info = (Comment) commentList.getItemAtPosition(i);
                EditCommentFragment editCommentFragment = EditCommentFragment.newInstance(list_info);
                editCommentFragment.show(getSupportFragmentManager(), "EDIT COMMENT");
            }
        });
        commentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {
                final int selected_item = position;

                new AlertDialog.Builder(HabitEventActivity.this).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure...")
                        .setMessage("Do you want to delete the comment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                commentDataList.remove(selected_item);
                                commentAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No" , null).show();

                return true;
            }
        });


        final ImageButton addCommentButton = findViewById(R.id.add_comment);
        addCommentButton.setOnClickListener(v -> {
            new AddCommentFragment().show(getSupportFragmentManager(), "ADD COMMENT");
        });

        if(isServicesOK()){
            init();
        }

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do it later
                onBackPressed();
            }
        });
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 123);
        });
        if (ContextCompat.checkSelfPermission(HabitEventActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HabitEventActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },

                    123);
        }



    }
        private void init(){
            LocationImage = findViewById(R.id.locationImage);
            LocationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HabitEventActivity.this, MapsActivity.class);
                    startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // Get String data from Intent
                String returnString = data.getStringExtra("keyName");

                // Set text view with string
                TextView textView = (TextView) findViewById(R.id.textView4);
                textView.setText(returnString);
            }
        }

        if (requestCode == 123) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
        }
    }
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
    public void onOkPressed(Comment newCity){
        commentAdapter.add(newCity);}

    @Override
    public void onEditOkPressed(Comment newCity){
        commentDataList.remove(position);
        commentAdapter.insert(newCity, position);
    }
    @Override
    public void onCancelPressed(){}


}
