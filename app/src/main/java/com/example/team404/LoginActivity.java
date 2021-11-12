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

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    static protected DocumentReference currentUserRef = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();

        signup_btn = findViewById(R.id.signup_btn);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        signup_btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //setContentView(R.layout.register);

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        db = FirebaseFirestore.getInstance();
        sign_in_btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String emailAddress = user_email.getText().toString();
                final String userPassword = user_pass.getText().toString();

                if (emailAddress.equals("")||userPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Please input email and password!", Toast.LENGTH_SHORT).show();
                }



                mAuth.signInWithEmailAndPassword(emailAddress, userPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser real_user = mAuth.getCurrentUser();
                                    currentUserRef = db.collection("users").document(real_user.getEmail());
                                    String userEmail = mAuth.getCurrentUser().getEmail();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "password/email is incorrect!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Log in failed!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });



                /*

                //check length for email
                DocumentReference docRef = db.collection("User").document(emailAddress);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String real_passward=document.getString("userPassword").toString();
                                        if (real_passward.equals(userPassword)){
                                            Toast.makeText(LoginActivity.this, "welcome!",
                                            Toast.LENGTH_SHORT).show();
                                            //setContentView(R.layout.activity_main);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            return;
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this, "password is incorrect!",
                                            Toast.LENGTH_SHORT).show();}
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Not find this account!",
                                        Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                 */



            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
}
