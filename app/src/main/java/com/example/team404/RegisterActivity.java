package com.example.team404;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{
    private Button reg_btn;
    private EditText password;
    private EditText repassword;
    private EditText phone;
    private EditText email;
    private EditText name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();


        reg_btn = findViewById(R.id.reg_btn);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassward);
        phone = findViewById(R.id.reg_phone);
        name = findViewById(R.id.reg_name);
        //final FirebaseFirestore db;
        //db = FirebaseFirestore.getInstance();
        //final CollectionReference collectionReference = db.collection("User");

        reg_btn.setOnClickListener( new View.OnClickListener(){
        @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString();
                String userPassword = password.getText().toString();
                String userName = name.getText().toString();
                String userRepassword = repassword.getText().toString();
                String userphone = phone.getText().toString();
                //https://stackoverflow.com/questions/8204680/java-regex-email
                //Nov 20, 2011
                //author:Jason Buberel;
                //check email form
                if ( emailAddress != null && Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(emailAddress).find()){

                }else{
                    Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.length()==0){
                    Toast.makeText(RegisterActivity.this, "user name is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userphone.length()==0){
                    Toast.makeText(RegisterActivity.this, "user phone is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPassword.length()==0){
                    Toast.makeText(RegisterActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if (userPassword.length()< 6){
                    Toast.makeText(RegisterActivity.this, "password must contain at least 6 character", Toast.LENGTH_SHORT).show();
                    return;
                }else if (userPassword.length()>10){
                    Toast.makeText(RegisterActivity.this, "password must contain at moat 10 character", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userRepassword==null){
                    Toast.makeText(RegisterActivity.this, "Re-password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!userPassword.equals(userRepassword)){
                    Toast.makeText(RegisterActivity.this, "password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(emailAddress, userPassword)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser firebaseuser = mAuth.getCurrentUser();
                                    Map<String, Object> city = new HashMap<>();
                                    city.put("userPassword", userPassword);
                                    city.put("emailAddress", emailAddress);
                                    city.put("userName", userName);
                                    city.put("Habits", "");
                                    city.put("Habitsevent", "");
                                    city.put("phone",userphone);
                                    //city.put("requestedList", Collections.emptyList());
                                    //city.put("followingList", Collections.emptyList());


                                    FirebaseFirestore db= FirebaseFirestore.getInstance();
                                    CollectionReference collectionReference = db.collection("User");

                                    collectionReference
                                            .document(emailAddress)
                                            .set(city)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written!");

                                                    //setContentView(R.layout.login);
                                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                    Toast.makeText(RegisterActivity.this, "Sign up failed!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }else{
                                    //Toast.makeText(RegisterActivity.this, userPassword, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterActivity.this, "email already exist!", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterActivity.this, "Sign up failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                            );
                /*
                // Get a top level reference to the collection
                Map<String, Object> city = new HashMap<>();
                city.put("userPassword", userPassword);
                city.put("emailAddress", emailAddress);
                city.put("userName", userName);
                city.put("Habits", "");
                city.put("Habitsevent", "");
                city.put("phone",userphone);
                collectionReference
                        .document(emailAddress)
                        .set(city)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                //setContentView(R.layout.login);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
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

