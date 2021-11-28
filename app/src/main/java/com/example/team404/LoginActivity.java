package com.example.team404;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    //--------------------------------
    //Login the user account
    //--------------------------------

    private Button signup_btn;
    private Button sign_in_btn;
    private EditText user_email;
    private EditText user_pass;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    static protected DocumentReference currentUserRef = null;
    String TAG =".LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        signup_btn = findViewById(R.id.signup_btn);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        signup_btn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        db = FirebaseFirestore.getInstance();
        sign_in_btn.setOnClickListener(view -> {
            final String emailAddress = user_email.getText().toString();
            final String userPassword = user_pass.getText().toString();

            if (emailAddress.length() ==0||userPassword.length()==0){
                Toast.makeText(LoginActivity.this, "Please input email and password!", Toast.LENGTH_SHORT).show();
                return;
            }



            mAuth.signInWithEmailAndPassword(emailAddress, userPassword)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                                currentUserRef = db.collection("User").document(user.getEmail());

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "password/email is incorrect!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Log in failed!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });







        });
    }


}
