package com.example.team404;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

public class AccountEditActivity extends AppCompatActivity {
    //--------------------------------
    //Change account information
    //email is unique id, cannot changed
    //previous activity: AccountActivity.java
    //--------------------------------
    private EditText usernameEditText;
    private TextView emailTextView;
    private EditText phoneEditText;
    private ImageView backImage;
    private ImageView saveImage;
    private ImageView profileView;
    Uri profile_uri;
    String uri_string;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String userEmail;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String timeStamp = new SimpleDateFormat("WW-yyyy-MM-dd-hh-mm-ss").format(new Date());
    StorageReference userRef = storageRef.child("/image_user/"+userEmail+timeStamp+".png");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        userEmail = user.getEmail();


        setContentView(R.layout.activity_change_account);
        usernameEditText = findViewById(R.id.nameEditText);
        emailTextView = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        backImage = findViewById(R.id.backImage);
        profileView = findViewById(R.id.profile);
        emailTextView.setText(userEmail);
        Bundle extras = getIntent().getExtras();
        //String profile_uri = extras.getString("profile_uri");
        String phone = extras.getString("phone");
        uri_string = extras.getString("profile_uri");
        String name = extras.getString("name");
        usernameEditText.setText(name);
        phoneEditText.setText(phone);
        System.out.println("-------------00----------"+profile_uri);
        if (uri_string != null) {
            Uri storageURL = Uri.parse(uri_string);
            Glide.with(getApplicationContext()).load(storageURL).into(profileView);
            System.out.println("---------------------------storage url:"+storageURL);

        }

        System.out.println("-------------0000----------"+profile_uri);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle extras = new Bundle();
                extras.putString("editPhone", phone);
                extras.putString("uri_string",uri_string);
                extras.putString("editName", name);
                intent.putExtras(extras);
                setResult(333, intent);
                finish();
                onBackPressed();

            }
        });

        profileView.setOnClickListener(v -> {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(AccountEditActivity.this);
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
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = usernameEditText.getText().toString();
                String newPhone = phoneEditText.getText().toString();

                System.out.println("-------------44----------"+profile_uri);


                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference userDocRef = db.collection("User").document(userEmail);
                userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(profile_uri!=null){
                                System.out.println("-------------1111111------1----"+profile_uri);
                                userRef.putFile(profile_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        userRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                System.out.println("-------------11222222222----------"+profile_uri);
                                                uri_string = uri.toString();
                                                System.out.println("-------------3333333333333---------"+uri_string);
                                                System.out.println("---------------------------uid:"+user.getUid());
                                                userDocRef.update("userName", newName,"phone", newPhone, "profile_uri", uri_string );
                                                Intent intent = new Intent();
                                                Bundle extras = new Bundle();
                                                extras.putString("editPhone", newPhone);
                                                extras.putString("uri_string",uri_string);
                                                extras.putString("editName", newName);
                                                intent.putExtras(extras);
                                                setResult(333, intent);

                                                finish();
                                                onBackPressed();
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
                                        Intent intent = new Intent();
                                        Bundle extras = new Bundle();
                                        extras.putString("editPhone", newPhone);
                                        extras.putString("uri_string",uri_string);
                                        extras.putString("editName", newName);
                                        intent.putExtras(extras);
                                        setResult(333, intent);

                                        onBackPressed();

                                    }
                                });


                            }else{
                                userDocRef.update("userName", newName,"phone", newPhone, "profile_uri", uri_string );
                                Intent intent = new Intent();
                                Bundle extras = new Bundle();
                                extras.putString("editPhone", newPhone);
                                extras.putString("uri_string",uri_string);
                                extras.putString("editName", newName);
                                intent.putExtras(extras);
                                setResult(333, intent);
                                finish();
                                onBackPressed();
                            }

                    }
                });

            }
        });

    }
        private void openCamera() {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
            profile_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            System.out.println("-------------5555----------"+profile_uri);

            //Camera intent
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,profile_uri);
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
                        Toast.makeText(AccountEditActivity.this, "Permission Denied...", Toast.LENGTH_LONG).show();
                    }
            }
        }
    //Waiting for next activity to pass string back in to the current activity
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {

        if (requestCode==111){
            if (resultCode == Activity.RESULT_OK) {
                profileView.setImageURI(profile_uri);
                System.out.println("-------------11----------"+profile_uri);
                System.out.println("---------------------------uid:"+user.getUid());
                try {
                    Bitmap captureImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profile_uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    captureImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    System.out.println("-------------11------1----"+userRef);
                    UploadTask uploadTask = userRef.putBytes(imageData);
                    System.out.println("-------------11------2----"+userRef);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AccountEditActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AccountEditActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    System.out.println("-------------11111111111111111111111111111--------");
                    e.printStackTrace();
                }
            }
        }else if(requestCode==222){
            if (resultCode == Activity.RESULT_OK) {

                try {
                    profile_uri = data.getData();
                    profileView.setImageURI(profile_uri);
                    System.out.println("-------------22----------"+profile_uri);
                    System.out.println("---------------------------uid:"+user.getUid());
                    Bitmap captureImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), profile_uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    captureImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] imageData = baos.toByteArray();
                    UploadTask uploadTask = userRef.putBytes(imageData);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AccountEditActivity.this, "Upload Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AccountEditActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }



    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }




}
