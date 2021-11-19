package com.example.team404;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private ImageView backImage;
    private ListView requestedListView;
    private List<String> requestsList;
    private RequestsListAdapter adapter;
    private FirebaseFirestore db;
    private DocumentReference userDocRef;
    String requesterEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        db= FirebaseFirestore.getInstance();
        userDocRef = db.collection("User").document(userEmail);
        /*
        Currently working on this code, not working at the moment

        userDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    String requesterEmail = (String)value.getData().get("userName");
                }
            }
        });


         */

        // This list is for testing only
        List<String> requestsList2 = Arrays.asList("andrei@gmail.com", "ta@ta.com");

        adapter = new RequestsListAdapter(this, R.layout.follow_request_layout ,requestsList2);
        requestedListView = findViewById(R.id.requestListView);
        requestedListView.setAdapter(adapter);

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
