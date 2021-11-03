package com.example.team404;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {



    private Button signup_btn;
    private Button sign_in_btn;
    private EditText user_email;
    private EditText user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        signup_btn = findViewById(R.id.signup_btn);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);

        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        sign_in_btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String emailAddress = user_email.getText().toString();
                final String userPassword = user_pass.getText().toString();
                DocumentReference docRef = db.collection("User").document(emailAddress);
                CollectionReference citiesRef = db.collection("User");
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
//                                        Query capitalCities = db.collection("User").whereEqualTo("emailAddress", emailAddress);
//                                        Log.d(TAG, document.getId() + " => " + document.getData()+capitalCities);
                                        String real_pass=document.getString("userPassword");
                                        if (userPassword==real_pass){


                                        Toast.makeText(LoginActivity.this, "they are match!you are login",
                                                Toast.LENGTH_SHORT).show();
                                    }else{Toast.makeText(LoginActivity.this, "they are not match!",
                                                Toast.LENGTH_SHORT).show();}
                                    }else {   Toast.makeText(LoginActivity.this, "No this email account!",
                                            Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });



            }
        });
    }
}
