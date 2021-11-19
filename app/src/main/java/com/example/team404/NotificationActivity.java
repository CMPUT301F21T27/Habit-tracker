package com.example.team404;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import org.w3c.dom.Document;

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
    private String requesterListString;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        requesterListString = getIntent().getStringExtra("reqListString");

        // Check if list is empty
        if(requesterListString.length() > 2) {
            requesterListString = requesterListString.substring(1, requesterListString.length() - 1);
            requestsList = Arrays.asList(requesterListString.split(", "));
        }
        else{ requestsList = Arrays.asList();}

        // This list is for testing only
        //List<String> requestsList2 = Arrays.asList("andrei@gmail.com", "ta@ta.com", requesterListString);

        adapter = new RequestsListAdapter(this, R.layout.follow_request_layout ,requestsList);
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
