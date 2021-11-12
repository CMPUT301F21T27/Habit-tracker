package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AccountEditActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private TextView emailTextView;
    private EditText phoneEditText;
    private ImageView backImage;
    private ImageView saveImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();


        setContentView(R.layout.activity_change_account);
        usernameEditText = findViewById(R.id.nameEditText);
        emailTextView = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        backImage = findViewById(R.id.backImage);

        emailTextView.setText(userEmail);
        Bundle extras = getIntent().getExtras();
        String phone = extras.getString("phone");
        String name = extras.getString("name");
        usernameEditText.setText(name);
        phoneEditText.setText(phone);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Bundle extras = new Bundle();
                extras.putString("editPhone", phone);

                extras.putString("editName", name);
                intent.putExtras(extras);
                setResult(333, intent);
                onBackPressed();

            }
        });
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = usernameEditText.getText().toString();
                String newPhone = phoneEditText.getText().toString();
                Intent intent = new Intent();
                Bundle extras = new Bundle();
                extras.putString("editPhone", newPhone);

                extras.putString("editName", newName);
                intent.putExtras(extras);


                final FirebaseFirestore db;
                db= FirebaseFirestore.getInstance();
                DocumentReference userDocRef = db.collection("User").document(userEmail);
                userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()){

                            userDocRef.update("userName", newName);
                            userDocRef.update("phone", newPhone);
                        }else{
                            return;
                        }

                    }
                });


                setResult(333, intent);

                onBackPressed();
            }
        });





    }
}
