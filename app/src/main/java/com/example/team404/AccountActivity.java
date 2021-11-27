package com.example.team404;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AccountActivity extends AppCompatActivity {
    //--------------------------------
    //Display account information
    //--------------------------------
    private ImageView notifcationImage;
    private ImageView editImage;
    private ImageView changePwdImage;

    private TextView username;
    private TextView email;
    private TextView phone;
    private int log_out_count=0;
    private String requestedListString;
    private String old_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        final FirebaseFirestore db;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();

        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_account);
        bottomNav.setOnItemSelectedListener(navListener);

        username = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        email.setText(userEmail);

        db= FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("User").document(userEmail);
        userDocRef.addSnapshotListener((value, error) -> {
            if (value != null && value.exists()){
                old_password = value.getData().get("userPassword").toString();
                String userPhone = value.getData().get("phone").toString();
                String userName = value.getData().get("userName").toString();
                requestedListString = value.get("requestedList").toString();
                username.setText(userName);
                phone.setText(userPhone);
            }else{
                String userPhone = "Empty phone number";
                phone.setText(userPhone);
            }

        });


        notifcationImage = findViewById(R.id.notificationImage);
        notifcationImage.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, NotificationActivity.class);
            intent.putExtra("reqListString", requestedListString);
            startActivity(intent);
        });
        changePwdImage = findViewById(R.id.change_password_Image);
        changePwdImage.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, AccountPwdEditActivity.class);
            intent.putExtra("old_password", old_password);
            startActivity(intent);
        });

        editImage = findViewById(R.id.EditImage);
        editImage.setOnClickListener(v -> {

            String name = username.getText().toString();
            String  phone_ = phone.getText().toString();

            Intent intent = new Intent(AccountActivity.this, AccountEditActivity.class);
            Bundle extras = new Bundle();
            extras.putString("phone", phone_);
            extras.putString("name", name);
            intent.putExtras(extras);
            startActivityForResult(intent, 333);
        });

        Button logoutButton = findViewById(R.id.log_out_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finishAffinity();
                log_out_count++;
                if (log_out_count >1){
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(AccountActivity.this, "Press again to exit!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                String returnString = data.getStringExtra("editName");
                String returnphone = data.getStringExtra("editPhone");

                // Set text view with string
                TextView textView = (TextView) findViewById(R.id.name);
                textView.setText(returnString);
                TextView textView1 = (TextView) findViewById(R.id.phone);
                textView1.setText(returnphone);
            }
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
                            return true;
                            //;
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
                            //return true;
                            break;
                    }
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
            };
    private int count = 0;
    @Override
    public void onBackPressed() {
        count++;
        if (count >1){
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            AccountActivity.super.onBackPressed();
                            finishAffinity();
                        }
                    }).create().show();
        }else{

        }

    }
}
