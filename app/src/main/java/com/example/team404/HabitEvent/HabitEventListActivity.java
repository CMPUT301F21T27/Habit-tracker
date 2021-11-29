package com.example.team404.HabitEvent;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
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
import androidx.core.widget.ContentLoadingProgressBar;

import com.example.team404.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HabitEventListActivity extends AppCompatActivity {
    //--------------------------------
    //Display habit event list
    // If today is on the plan, we can add new habit event to habit
    //--------------------------------
    ListView habitEventList;
    ArrayAdapter<HabitEvent> habitEventArrayAdapter;
    ArrayList<HabitEvent> habitEventDataList;
    private ImageView backImage;
    private ImageView addImage;
    private int position;
    private String current_habit_id;
    private  String today;
    private String Cloud_location;
    private String Cloud_comment;
    private  String Cloud_photo;
    private int total_did;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        final FirebaseFirestore db;
        setContentView(R.layout.activity_habit_event_list);
        ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.progress_bar);
        contentLoadingProgressBar.show();
        habitEventList= findViewById(R.id.habit_event_list);
        habitEventDataList=new ArrayList<>();
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);

        Intent current_habit_intent = getIntent();
        current_habit_id = current_habit_intent.getStringExtra("current_habit_id");
        today =current_habit_intent.getStringExtra("today");
        System.out.println("--------------------------------1---"+current_habit_id);
        System.out.println("--------------------------------11---"+today);



        ImageView addImage = findViewById(R.id.addImage);
        //addImage.setVisibility(today.equals("today")? ImageView.VISIBLE: ImageView.INVISIBLE );

        System.out.println("----------------1111111111111111111-------------------");
        System.out.println("--------------------------------1---"+current_habit_id);
        System.out.println("--------------------------------11---"+today);
        db = FirebaseFirestore.getInstance();
        DocumentReference habitDoc = FirebaseFirestore.getInstance().collection("Habit").document(current_habit_id);

        db.collection("Habit Event List")
                .whereEqualTo("OwnerReference" , habitDoc)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "listen failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d(TAG, "Current habit event: " + habitDoc);

                        contentLoadingProgressBar.show();
                        habitEventDataList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if ( doc.get("Location") == null){
                                Cloud_location = "No location provide";
                            }else{
                                Cloud_location = String.valueOf(doc.getData().get("Location"));
                            }
                            if ( doc.get("Comment") == null){
                                Cloud_comment = "No comment provide";
                            }else{
                                Cloud_comment = String.valueOf(doc.getData().get("Comment"));
                            }
                            if ( doc.get("Uri") == null){
                                Cloud_photo = null;
                            }else{
                                Cloud_photo = String.valueOf(doc.getData().get("Uri"));
                            }


                            String id = doc.getId();
                            String date = String.valueOf(doc.getData().get("Date"));
                            habitEventDataList.add(new HabitEvent(id,  Cloud_photo, Cloud_location, Cloud_comment, date));



                        }
                        contentLoadingProgressBar.hide();
                        System.out.println("--++++++--");
                        if (habitEventDataList.size()== 0 && today.equals("today") ){
                                addImage.setVisibility(ImageView.VISIBLE);

                        }else{
                            if(today.equals("today")) {
                                String valid_until = habitEventDataList.get(habitEventDataList.size() - 1).getDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String valid_now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                                Date strDate = null;
                                Date current_Date = null;

                                try {

                                    strDate = sdf.parse(valid_until);
                                    current_Date = sdf2.parse(valid_now);
                                } catch (Exception pe) {
                                    pe.printStackTrace();
                                }
                                addImage.setVisibility(current_Date.after(strDate) ? ImageView.VISIBLE : ImageView.INVISIBLE);
                            }
                        }



                        habitEventArrayAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Current habit event: " + habitDoc);
                    }
                });
        System.out.println("----");
        //addImage.setVisibility(today.equals("today")? ImageView.VISIBLE: ImageView.INVISIBLE );
        System.out.println("--++++--");
        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position =i;
                HabitEvent currentHabitevent = habitEventDataList.get(i);
                String location = currentHabitevent.getLocation();
                String comment = currentHabitevent.getComments();
                String id = currentHabitevent.getId();
                String date = currentHabitevent.getDate();
                String uri =  currentHabitevent.getUri();
                System.out.println("--------1111--------------------"+uri);

                Intent intent = new Intent(HabitEventListActivity.this, HabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", id);
                extras.putString("location", location);
                extras.putString("date", date);
                extras.putString("storageUrlString",uri);
                extras.putString("comment", comment);
                extras.putString("today",today);
                extras.putString("current_habit_id",current_habit_id);
                intent.putExtras(extras);
                System.out.println("---------2222-------------------"+uri+"---------");

                startActivity(intent);
            }
        });
        habitEventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {
                final int selected_item = position;

                new AlertDialog.Builder(HabitEventListActivity.this).
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
                                if (habitEventDataList.size()== 0 && today.equals("today") ){
                                    addImage.setVisibility(ImageView.VISIBLE);

                                }
                                db.collection("Habit Event List").document(current_habit_event_id)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                Toast.makeText(getApplicationContext(), "Delete successfully!", Toast.LENGTH_SHORT).show();
                                                db.collection("Habit").document(current_habit_id).
                                                        get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                                total_did=Integer.valueOf(documentSnapshot.getData().get("Total Did").toString());
                                                                total_did=total_did-1;
                                                                db.collection("Habit").document(current_habit_id).
                                                                        update("Total Did",total_did);


                                                            }

                                                        });
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

        //addImage.setVisibility(today.equals("today")? ImageView.VISIBLE: ImageView.INVISIBLE );
        backImage = findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitEventListActivity.this, AddHabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("habitId", current_habit_id);

                intent.putExtras(extras);
                startActivityForResult(intent, 000);
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if (requestCode == 000) {
            if (resultCode == RESULT_OK) {
                String returnString = data.getStringExtra("Disappear add button");
                System.out.println("--++++=====--");
                ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.progress_bar);
                contentLoadingProgressBar.show();
                addImage.setVisibility(returnString.equals("true")? ImageView.INVISIBLE: ImageView.VISIBLE );
                contentLoadingProgressBar.hide();


            }
        }
    }


}
