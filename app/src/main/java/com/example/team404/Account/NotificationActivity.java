package com.example.team404.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team404.R;

import java.util.Arrays;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    //-------------------------------------
    //get notification message from other doers
    // other doers will send a follow request
    //user can decide agree or disagree
    //-------------------------------------
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
        //List<String> requestsList = Arrays.asList("ad@gmail.com", "ta@ta.com");

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
