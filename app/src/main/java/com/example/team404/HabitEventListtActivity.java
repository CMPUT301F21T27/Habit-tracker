package com.example.team404;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HabitEventListtActivity extends AppCompatActivity {
    ListView habitEventList;
    ArrayAdapter<HabitEvent> habitEventArrayAdapter;
    ArrayList<HabitEvent> habitEventDataList;
    private ImageView backImage;
    private ImageView addImage;
    private int position ;
    private String current_habit_id;
    private  String today;
    private String Cloud_location;
    private String Cloud_comment;
    //--------------------------------------------------------------------//
    //I will do later after add firesbase database.
    // Once I get habit id and I will create habit event Id, I can pass all varable to the another activty after firebase
    // --------------------------------------------------------------------//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        final FirebaseFirestore db;
        setContentView(R.layout.activity_habit_event_listt);
        habitEventList=(ListView) findViewById(R.id.habit_event_list);
        habitEventDataList=new ArrayList<>();
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        //-----------
        // read habit id and create habit event id here
        Intent current_habit_intent = getIntent();
        current_habit_id = current_habit_intent.getStringExtra("current_habit_id");
        today =current_habit_intent.getStringExtra("Today");

        db = FirebaseFirestore.getInstance();
        DocumentReference habitDoc = FirebaseFirestore.getInstance().collection("Habit").document(current_habit_id);

        db.collection("Habit Event List")
                .whereEqualTo("OwnerReference" , habitDoc)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "listen faild", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d(TAG, "Current habit event: " + habitDoc);


                        habitEventDataList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if ( doc.get("Location") == null){
                                Cloud_location = "No location provide";
                            }
                            //if ( doc.get("Photo") == null){
                            //    Cloud_location = "No photo provide";
                            //}
                            if ( doc.get("Comment") == null){
                                Cloud_comment = "No comment provide";
                            }
                            Log.d(TAG, "Current habit eventuuuuuu: " + String.valueOf(doc.getData().get("OwnerReference")));

                            String id = doc.getId();
                            Cloud_location = String.valueOf(doc.getData().get("Location"));
                            Cloud_comment = String.valueOf(doc.getData().get("Comment"));
                            String date = String.valueOf(doc.getData().get("Date"));


                            habitEventDataList.add(new HabitEvent(id, Cloud_location, Cloud_comment, date));
                            Log.d(TAG, "Current habit event44: " + habitEventDataList.size());
                            //habitEventList.setAdapter(habitEventArrayAdapter);
                            Log.d(TAG, "Current habit eventuurty6y6y6uuuu: " + habitEventDataList.size());


                        }
                        habitEventArrayAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Current habit event: " + habitDoc);
                    }
                });







        //-------------
        //habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        //habitEventList.setAdapter(habitEventArrayAdapter);

        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position =i;
                HabitEvent currentHabitevent = habitEventDataList.get(i);
                String location = currentHabitevent.getLocation();
                String comment = currentHabitevent.getComments();
                String id = currentHabitevent.getId();
                String date = currentHabitevent.getDate();
                Intent intent = new Intent(HabitEventListtActivity.this, HabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", current_habit_id);
                extras.putString("location", location);
                extras.putString("date", date);
                extras.putString("comment", comment);
                intent.putExtras(extras);

                startActivityForResult(intent, 111);
            }
        });
        habitEventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {
                final int selected_item = position;

                new AlertDialog.Builder(HabitEventListtActivity.this).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                HabitEvent currentHabitevent = habitEventDataList.get(selected_item);
                                String current_habit_event_id = currentHabitevent.getId();
                                habitEventDataList.remove(selected_item);
                                habitEventArrayAdapter.notifyDataSetChanged();
                                db.collection("Habit Event List").document(current_habit_event_id)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                Toast.makeText(getApplicationContext(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No" , null).show();

                return true;
            }
        });
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        habitEventList.setAdapter(habitEventArrayAdapter);

        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addImage = findViewById(R.id.addImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitEventListtActivity.this, AddHabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("habitId", current_habit_id);

                intent.putExtras(extras);
                startActivityForResult(intent, 000);
            }
        });
    }
    //https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
    // author: Suragch (answered) GabrielBB(edited)
    //date: 11-5-2021; 12-13-2021
    // protected void onActivityResult(int requestCode, int resultCode, Intent data) is created by Suragch
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 000) {
            // Get String data from Intent
            String returnId = data.getStringExtra("addId");
            String returnString = data.getStringExtra("addTitle");
            String returnDate = data.getStringExtra("addDate");
            String returnComment = data.getStringExtra("addComment");
            if (returnString.length()!=0 || returnComment.length()!=0){
                HabitEvent newhabitEvent = new HabitEvent(returnId, returnString ,returnComment, returnDate);
                //habitEventDataList.add(newhabitEvent);
                //habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
                //habitEventList.setAdapter(habitEventArrayAdapter);
                //habitEventArrayAdapter.notifyDataSetChanged();
            }else if (returnString.length()==0 && returnComment.length()==0){
                Toast.makeText(this, "please add comment/location", Toast.LENGTH_SHORT).show();
                return;
            }



        }
        if (requestCode == 111) {

            // Get String data from Intent
            String returnId = data.getStringExtra("editId");
            String returnString = data.getStringExtra("editTitle");
            String returnDate = data.getStringExtra("editDate");
            String returnComment = data.getStringExtra("editComment");

            if (returnString.length()!=0 || returnComment.length()!=0){
                HabitEvent newhabitEvent = new HabitEvent(returnId, returnString ,returnComment, returnDate);
                //habitEventDataList.remove(position);

                //habitEventDataList.set(position, newhabitEvent );
                //habitEventList.setAdapter(habitEventArrayAdapter);
                //habitEventArrayAdapter.notifyDataSetChanged();
            }else if (returnString.length()==0 && returnComment.length()==0){
                Toast.makeText(this, "please add comment/location", Toast.LENGTH_SHORT).show();
                return;
            }



        }
    }
}
