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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private EditText password;
    private EditText repassword;
    private EditText phone;
    private EditText email;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.reg_btn);
        register.setOnClickListener(this);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassward);
        phone = findViewById(R.id.reg_phone);
        name = findViewById(R.id.reg_phone);


    }

    public Boolean isEmail(String str) {
        boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }


    @Override
    public void onClick(View view) {
        String emailAddress = email.getText().toString();
        String userPassword = password.getText().toString();
        String userName = name.getText().toString();
        String userRepassword = repassword.getText().toString();
        final FirebaseFirestore db;


        if (view.getId() == R.id.reg_btn) {
            if (TextUtils.isEmpty(emailAddress) || !isEmail(emailAddress)) {
                Toast.makeText(RegisterActivity.this, " email account error!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(userName)) {
                name.setError("Name is required!");
                name.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(phone.getText().toString())) {
                phone.setError("Phone number is required!");
                phone.requestFocus();
                return;

            }
            // Access a Cloud Firestore instance from your Activity
            db = FirebaseFirestore.getInstance();
            // Get a top level reference to the collection
            Map<String, Object> city = new HashMap<>();
            city.put("emailAddress", emailAddress);
            city.put("userPassword", userPassword);
            city.put("userName", userName);
            city.put("Habits", "");
            city.put("Habitsevent", "userName");
            db.collection("User").document(emailAddress)
                    .set(city)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });


        }
    }
}