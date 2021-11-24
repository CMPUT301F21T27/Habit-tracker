package com.example.team404;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AccountPwdEditActivity extends AppCompatActivity {

    private TextView usernameEditText;
    private TextView emailTextView;
    private TextView phoneEditText;
    private EditText changePwdEditText;
    private  EditText oldPwdEditText;
    private ImageView backImage;
    private ImageView saveImage;
    private String userOldPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);


        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();


        setContentView(R.layout.activity_change_account_pwd);
        emailTextView = findViewById(R.id.emailEditText);
        backImage = findViewById(R.id.backImage);
        changePwdEditText = findViewById(R.id.change_password_EditText);
        oldPwdEditText = findViewById(R.id.old_psw_EditText);
        emailTextView.setText(userEmail);
        /*
        final FirebaseFirestore db;
        db= FirebaseFirestore.getInstance();
        final DocumentReference userDocRef1 = db.collection("User").document(userEmail);
        userDocRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    userOldPwd = value.getData().get("userPassword").toString();

                }else{
                    return;
                }

            }
        });

         */



        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        saveImage = findViewById(R.id.saveImage);
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore db;
                db= FirebaseFirestore.getInstance();
                DocumentReference userDocRef = db.collection("User").document(userEmail);

                String newPwd = changePwdEditText.getText().toString();
                String oldPws = oldPwdEditText.getText().toString();

                userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()){

                            userOldPwd = value.getData().get("userPassword").toString();
                            if (oldPws.equals(userOldPwd)){
                                if (newPwd.length()==0){
                                    Toast.makeText(AccountPwdEditActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if (newPwd.length()< 6){
                                    Toast.makeText(AccountPwdEditActivity.this, "password must contain at least 6 character", Toast.LENGTH_SHORT).show();
                                    return;
                                }else if (newPwd.length()>10){
                                    Toast.makeText(AccountPwdEditActivity.this, "password must contain at moat 10 character", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                user.updatePassword(newPwd)
                                    .addOnCompleteListener(AccountPwdEditActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //userDocRef.update("userPassword", newPwd);
                                                Toast.makeText(getApplicationContext(), "User Password Updated.", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "For Securtiy resons you have to re-login first.\nThen try to update password", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                        }
                                    });
                                userDocRef.update("userPassword", newPwd);
                                Toast.makeText(AccountPwdEditActivity.this, " Change password successfully!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else{
                                Toast.makeText(AccountPwdEditActivity.this, "Old password does not match!", Toast.LENGTH_SHORT).show();
                                return;

                            }




                        }else{
                            Toast.makeText(AccountPwdEditActivity.this, "Ops, try agian!", Toast.LENGTH_SHORT).show();

                            return;
                        }

                    }
                });


            }
        });





    }
}
