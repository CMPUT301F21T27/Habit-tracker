package com.example.team404;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{
    private Button reg_btn;
    private EditText password;
    private EditText repassword;
    private EditText phone;
    private EditText email;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        reg_btn = findViewById(R.id.reg_btn);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassward);
        phone = findViewById(R.id.reg_phone);
        name = findViewById(R.id.reg_phone);
        final FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("User");

        reg_btn.setOnClickListener( new View.OnClickListener(){
        @Override
            public void onClick(View view) {
                final String emailAddress = email.getText().toString();
                final String userPassword = password.getText().toString();
                final String userName = name.getText().toString();
                final String userRepassword = repassword.getText().toString();
                final String userphone = phone.getText().toString();
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



            }
        });
    }
}

