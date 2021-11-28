package com.example.team404;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddHabitEventActivity extends AppCompatActivity implements AddCommentFragment.onFragmentInteractionListener, EditCommentFragment.onFragmentInteractionListener  {
    //--------------------------------
    //add new habit event if today is on the plan
    //add optional photo, optional location, optional comment
    //previous activity: HabitEventListActivity.java
    //--------------------------------
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private ImageView backImage;
    private ImageView LocationImage;
    private ImageView imageView;
    private ImageView saveImage;
    private TextView commentTextView;
    private TextView locationTextView;
    Uri image_uri;
    String uri_string;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userEmail = user.getEmail();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-mm-ss").format(new Date());
    StorageReference eventsHabbitRef = storageRef.child("/image/"+userEmail+timeStamp+".png");

    private int total_did;
    private static final String TAG = "AddHabitEventActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_event);
        Bundle extras = getIntent().getExtras();
        String habitId = extras.getString("habitId");
        DocumentReference habitDoc = FirebaseFirestore.getInstance().collection("Habit").document(habitId);


        commentTextView= findViewById(R.id.comment_textView);
        locationTextView = findViewById(R.id.location_textView);


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

                Intent intent = new Intent();

                Bundle extras = new Bundle();
                extras.putString("editId", "");
                extras.putString("editTitle", "");

                extras.putString("editDate", "");
                extras.putString("editComment", "");
                extras.putString("Disappear add button", "false");
                intent.putExtras(extras);
                setResult(000, intent);
                onBackPressed();
            }
        });
        //save habit event after press save button
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();

                db.collection("Habit").document(habitId).
                        get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                total_did=Integer.valueOf(documentSnapshot.getData().get("Total Did").toString());
                                total_did=total_did+1;
                                db.collection("Habit").document(habitId).
                                        update("Total Did",total_did);


                            }

                        });



                String current_location= locationTextView.getText().toString();
                String current_comment= commentTextView.getText().toString();
                Date date = new Date();
                String habit_event_id = String.valueOf(date);
                String date_ = new SimpleDateFormat("yyyy-MM-dd").format(date);


                if (image_uri!=null) {

                    eventsHabbitRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            eventsHabbitRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    uri_string = uri.toString();
                                    Map<String, Object> event = new HashMap<>();
                                    event.put("Id", habit_event_id);
                                    event.put("Location", current_location);
                                    event.put("Comment", current_comment);
                                    event.put("Date", date_);
                                    event.put("OwnerReference", habitDoc);
                                    event.put("url", image_uri.toString());
                                    event.put("Uri", uri_string);
                                    event.put("Habit Id", habitId);
                                    Intent intent = new Intent();
                                    Bundle extras = new Bundle();
                                    extras.putString("addId", habit_event_id);
                                    extras.putString("addTitle",current_location);
                                    extras.putString("addDate", date_);
                                    extras.putString("addComment", current_comment);
                                    extras.putString("Disappear add button", "true");
                                    intent.putExtras(extras);
                                    setResult(000, intent);


                                    db.collection("Habit Event List").document(habit_event_id)
                                            .set(event)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.w(TAG, "success add to fireBase");
                                                    db.collection("Habit").document(habitId).
                                                            get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    total_did=Integer.valueOf(documentSnapshot.getData().get("Total Did").toString());
                                                                    total_did=total_did+1;
                                                                    db.collection("Habit").document(habitId).
                                                                            update("Total Did",total_did);
                                                                }

                                                            });
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "failed add to fireBase", e);
                                                }
                                            });


                                    AddHabitEventActivity.this.finish(); // finish current activity
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Get photo url failed!");

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("upload photo failed!");

                        }
                    });
                }else{
                    Map<String, Object> event = new HashMap<>();

                    event.put("Location", current_location);
                    event.put("Comment", current_comment);
                    event.put("Date", date_);
                    event.put("OwnerReference", habitDoc);
                    event.put("url","");
                    event.put("Uri", "");
                    event.put("Habit Id", habitId);
                    Intent intent = new Intent();
                    Bundle extras = new Bundle();
                    extras.putString("addId", habit_event_id);
                    extras.putString("addTitle",current_location);
                    extras.putString("addDate", date_);
                    extras.putString("addComment", current_comment);
                    extras.putString("Disappear add button", "true");
                    intent.putExtras(extras);
                    setResult(000, intent);


                    db.collection("Habit Event List").document(habit_event_id)
                            .set(event)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.w(TAG, "success add to fireBase");
                                    db.collection("Habit").document(habitId).
                                            get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    total_did=Integer.valueOf(documentSnapshot.getData().get("Total Did").toString());
                                                    total_did=total_did+1;
                                                    db.collection("Habit").document(habitId).
                                                            update("Total Did",total_did);
                                                }

                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "failed add to fireBase", e);
                                }
                            });

                    AddHabitEventActivity.this.finish(); // finish current activity
                }

                onBackPressed();
            }
        });
        // to call Camera to get a photo
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(AddHabitEventActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission, 1000);
                            } else {
                                openCamera();
                            }
                        } else {
                            openCamera();
                        }
                    } else if (options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                        startActivityForResult(intent, 222);

                    } else if (options[item].equals("Cancel")) {

                        dialog.dismiss();

                    }
                }
            });
            builder.show();
        });


    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //Camera intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(takePictureIntent, 111);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    //permission from pop up was denied.
                    Toast.makeText(AddHabitEventActivity.this, "Permission Denied...", Toast.LENGTH_LONG).show();
                }
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
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("keyName");

                locationTextView.setText(returnString);
            }
        }
        if (requestCode==111){
            if (resultCode == Activity.RESULT_OK) {
                imageView.setImageURI(image_uri);
                try {
                    Bitmap captureImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    captureImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    UploadTask uploadTask = eventsHabbitRef.putBytes(imageData);
                    Toast.makeText(AddHabitEventActivity.this, "Connecting internet...", Toast.LENGTH_LONG).show();
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddHabitEventActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddHabitEventActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode==222){
            if (resultCode == Activity.RESULT_OK) {

                try {
                    image_uri = data.getData();
                    imageView.setImageURI(image_uri);
                    Bitmap captureImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    captureImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    UploadTask uploadTask = eventsHabbitRef.putBytes(imageData);
                    Toast.makeText(AddHabitEventActivity.this, "Connecting internet...", Toast.LENGTH_LONG).show();
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddHabitEventActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddHabitEventActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
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
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
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
