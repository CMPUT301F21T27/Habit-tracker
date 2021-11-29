package com.example.team404.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.team404.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class RequestsListAdapter extends ArrayAdapter<String> {

    private Context reqContext;
    private List<String> requestsList;
    private String requesterName;
    private String requesterEmail;
    private FirebaseFirestore db;
    private DocumentReference requesterDocRef;
    private DocumentReference userDocRef;
    private RequestsListAdapter adapter;

    public RequestsListAdapter(@NonNull Context context, int resource ,List<String> objects) {
        super(context, resource , objects);
        this.requestsList = objects;
        reqContext = context;
        db= FirebaseFirestore.getInstance();
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
           view = LayoutInflater.from(reqContext).inflate(R.layout.follow_request_layout, parent, false);
        }

        TextView reqNameText = view.findViewById(R.id.follow_request_name);
        TextView reqEmailText = view.findViewById(R.id.follow_request_email);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userEmail = user.getEmail();
        userDocRef = db.collection("User").document(userEmail);

        requesterEmail = requestsList.get(position);
        requesterDocRef = db.collection("User").document(requesterEmail);
        requesterDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    requesterName = (String)value.getData().get("userName");
                    reqNameText.setText(requesterName);
                }
            }
        });
        reqEmailText.setText(requesterEmail);

        TextView result = view.findViewById(R.id.follow_result);

        final FloatingActionButton acceptButton = view.findViewById(R.id.follow_request_accept);
        final FloatingActionButton declineButton = view.findViewById(R.id.follow_request_decline);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesterEmail = requestsList.get(position);
                requesterDocRef = db.collection("User").document(requesterEmail);
                requesterDocRef.update("followingList", FieldValue.arrayUnion(userEmail));
                userDocRef.update("requestedList", FieldValue.arrayRemove(requesterEmail));
                acceptButton.setVisibility(v.INVISIBLE);
                declineButton.setVisibility(v.INVISIBLE);
                result.setText("Accepted");
                result.setVisibility(v.VISIBLE);
            }
        });
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesterEmail = requestsList.get(position);
                userDocRef.update("requestedList", FieldValue.arrayRemove(requesterEmail));
                acceptButton.setVisibility(v.INVISIBLE);
                declineButton.setVisibility(v.INVISIBLE);
                result.setText("Declined");
                result.setVisibility(v.VISIBLE);
            }
        });

        return view;

    }
}

