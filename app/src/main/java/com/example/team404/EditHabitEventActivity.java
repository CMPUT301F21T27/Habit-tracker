package com.example.team404;


import static android.content.ContentValues.TAG;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditHabitEventActivity extends AppCompatActivity implements AddCommentFragment.onFragmentInteractionListener, EditCommentFragment.onFragmentInteractionListener  {
    //--------------------------------
    //edit optional photo, optional location, optional comment
    //later activity: MapsActivity.java
    //previous activity: HabitEventActivity
    //--------------------------------
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private ImageView backImage;
    private ImageView LocationImage;
    private ImageView imageView;
    private ImageView saveImage;
    private TextView commentTextView;
    private TextView locationTextView;
    private String back_comment;
    private String back_location;
    private  String back_storageUrlString;
    private String habit_event_id;
    private String today;
    private String current_habit_id;
    private Boolean save_upload =false;
    Uri image_uri;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userEmail = user.getEmail();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String timeStamp = new SimpleDateFormat("WW-yyyy-MM-dd-hh-mm-ss").format(new Date());
    StorageReference eventsHabbitRef = storageRef.child("/image/"+userEmail+timeStamp+".png");


    private static final String TAG = "EditHabitEventActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit_event);
        ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.progress_bar);
        contentLoadingProgressBar.hide();


        Bundle extras = getIntent().getExtras();
        habit_event_id = extras.getString("id");
        back_location = extras.getString("location");
        back_comment = extras.getString("comment");
        today = extras.getString("today");
        current_habit_id = extras.getString("current_habit_id");
        back_storageUrlString = extras.getString("storageUrlString");


        commentTextView= findViewById(R.id.comment_textView);
        locationTextView = findViewById(R.id.location_textView);
        imageView=findViewById(R.id.imageView);
        saveImage=findViewById(R.id.saveImage);

        commentTextView.setText(back_comment);
        locationTextView.setText(back_location);
        System.out.println("---------------------------- Image file path is null!"+back_storageUrlString);
        if (back_storageUrlString!=null){
            Uri storageURL = Uri.parse(back_storageUrlString);
            Glide.with(getApplicationContext()).load(storageURL).into(imageView);
            System.out.println("----------------------------storage url:"+storageURL);
        }

        DocumentReference habitEventDoc = FirebaseFirestore.getInstance().collection("Habit Event List").document(habit_event_id);
        System.out.println("---------------------------- Image file path is null!"+habitEventDoc);

        commentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = commentTextView.getText().toString();
                if (info.length() ==0){
                    Toast.makeText(EditHabitEventActivity.this, "Please press add button to add comment.", Toast.LENGTH_SHORT).show();
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
                new AlertDialog.Builder(EditHabitEventActivity.this).
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
                new AddCommentFragment().show(getSupportFragmentManager(), "EDIT String");
            }

        });

        if(isServicesOK()){
            init();
        }

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location_ = locationTextView.getText().toString();
                String comment_ = commentTextView.getText().toString();
                finish();
                Intent intent = new Intent(EditHabitEventActivity.this, HabitEventActivity.class);
                //Intent intent = new Intent();
                Bundle extras = new Bundle();
                extras.putString("id", habit_event_id);
                extras.putString("location", location_);
                extras.putString("comment", comment_);
                extras.putString("storageUrlString",back_storageUrlString);
                intent.putExtras(extras);
                setResult(333, intent);

                System.out.println("--------path is null!"+image_uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                System.out.println("------------+++++++++++++++++++++++++++++"+image_uri);
                Toast.makeText(EditHabitEventActivity.this, "Nothing changed!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                overridePendingTransition(0, 0);
                //onBackPressed();
            }
        });




        final Handler handler = new Handler();
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //contentLoadingProgressBar.show();

                String location_ = locationTextView.getText().toString();
                String comment_ = commentTextView.getText().toString();
                System.out.println("-------------------rfrf--------- Image file path is null!"+image_uri);

                if (location_==null){
                    location_="No address record";
                }
                if(comment_==null){
                    comment_="No comment record";
                }
                System.out.println("-----------------00000----- "+comment_+location_);
                if (image_uri!=null) {
                    String finalLocation_ = location_;
                    String finalComment_ = comment_;
                    System.out.println("-----------------00000----- "+comment_+location_+image_uri.toString());
                    eventsHabbitRef.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            eventsHabbitRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println(" Get photo url success! URL: " + uri.toString());
                                    back_storageUrlString = uri.toString();
                                    final FirebaseFirestore db;
                                    db = FirebaseFirestore.getInstance();
                                    db.collection("Habit Event List").document(habit_event_id)
                                            .update("Location", finalLocation_, "Comment", finalComment_, "Uri", back_storageUrlString)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.w(TAG, "success add to fireBase");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "failed add to fireBase", e);
                                                }
                                            });

                                    EditHabitEventActivity.this.finish(); // finish current activity
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

                    Toast.makeText(EditHabitEventActivity.this, "Saving...", Toast.LENGTH_SHORT).show();
                    contentLoadingProgressBar.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*
                            System.out.println("-----------------------------------");
                            Intent intent = new Intent(EditHabitEventActivity.this, HabitEventListActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("current_habit_id", current_habit_id);
                            extras.putString("today", today);
                            intent.putExtras(extras);
                            //setResult(333, intent);
                            EditHabitEventActivity.this.startActivity(intent);
                            System.out.println("-----------------------t------------"+today);
                            System.out.println("----------------------c-------------"+current_habit_id);
                            EditHabitEventActivity.this.finish();
                            System.out.println("------------------------t-----------"+today);
                            System.out.println("------------------------c-----------"+current_habit_id);
                            overridePendingTransition(0,0);

                             */
                            EditHabitEventActivity.super.onBackPressed();
                            //saveImage.setEnabled(true);
                            //Toast.makeText(EditHabitEventActivity.this, "Saving...", Toast.LENGTH_SHORT).show();

                        }
                    }, 2000);



                }else{
                    contentLoadingProgressBar.show();
                    final FirebaseFirestore db;
                    db = FirebaseFirestore.getInstance();
                    db.collection("Habit Event List").document(habit_event_id)
                        .update("Location", location_, "Comment", comment_, "Uri", back_storageUrlString)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.w(TAG, "success add to firestore");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "failed add to firestore", e);
                            }
                        });

                    Toast.makeText(EditHabitEventActivity.this, "Saving...", Toast.LENGTH_SHORT).show();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //saveImage.setEnabled(true);
                                /*
                                System.out.println("-----------------------------------");
                                Intent intent = new Intent(EditHabitEventActivity.this, HabitEventListActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("current_habit_id", current_habit_id);
                                extras.putString("today", today);
                                intent.putExtras(extras);
                                //setResult(333, intent);
                                EditHabitEventActivity.this.startActivity(intent);
                                System.out.println("----------------------t-------------"+today);
                                System.out.println("-----------------------c------------"+current_habit_id);
                                EditHabitEventActivity.this.finish();
                                System.out.println("------------------------t-----------"+today);
                                System.out.println("------------------------c-----------"+current_habit_id);
                                overridePendingTransition(0,0);

                                 */
                                EditHabitEventActivity.super.onBackPressed();
                                //Toast.makeText(EditHabitEventActivity.this, "Saving...", Toast.LENGTH_SHORT).show();
                            }
                        }, 2000);

                }
            }
        });

        // to call Camera to get a photo
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(EditHabitEventActivity.this);
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
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(EditHabitEventActivity.this).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this photo?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                db.collection("Habit Event List").document(habit_event_id)
                                        .update("Uri",null)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                imageView.setImageResource(android.R.color.transparent);
                                                imageView=findViewById(R.id.imageView_delete);
                                                imageView.setVisibility(ImageView.VISIBLE);
                                                back_storageUrlString=null;
                                                image_uri=null;
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                Toast.makeText(getApplicationContext(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No" , null).show();

                return true;
            }
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
                    //permisiion from pop up was denied.
                    Toast.makeText(EditHabitEventActivity.this, "Permission Denied...", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(EditHabitEventActivity.this, MapsActivity.class);
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
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(EditHabitEventActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditHabitEventActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
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
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(EditHabitEventActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(EditHabitEventActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();
    }




}
