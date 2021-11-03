package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
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
            final CollectionReference collectionReference = db.collection("Cities");
            // Get a top level reference to the collection
            register.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Retrieving the city name and the province name from the EditText fields


                    HashMap<String, String> data = new HashMap<>();
                    data.put("User email", emailAddress);





        }

        switch (view.getId()){
            case R.id.reg_btn:
                startActivity(new Intent(this,LoginActivity.class));

        }
    }
}
