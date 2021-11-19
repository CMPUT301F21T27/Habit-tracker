package com.example.team404;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

// WILL ADD ACCEPT AND DENY FUNCTIONALITY BUTTONS LATER

public class RequestsListAdapter extends ArrayAdapter<String> {

    private Context reqContext;
    private List<String> requestsList;
    private String requesterName;
    private String requesterEmail;
    private FirebaseFirestore db;
    private DocumentReference requesterDocRef;

    public RequestsListAdapter(@NonNull Context context, int resource ,List<String> objects) {
        super(context, resource , objects);
        this.requestsList = objects;
        reqContext = context;
        db= FirebaseFirestore.getInstance();
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
        return view;

    }
}

