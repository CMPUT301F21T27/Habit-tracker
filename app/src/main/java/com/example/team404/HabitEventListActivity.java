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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HabitEventListActivity extends AppCompatActivity {
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
    private  String Cloud_photo;
    private String currentUri;
    //--------------------------------------------------------------------//
    //I will do later after add firesbase database.
    // Once I get habit id and I will create habit event Id, I can pass all varable to the another activty after firebase
    // --------------------------------------------------------------------//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        final FirebaseFirestore db;
        setContentView(R.layout.activity_habit_event_list);
        habitEventList=(ListView) findViewById(R.id.habit_event_list);
        habitEventDataList=new ArrayList<>();
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        //-----------
        // read habit id and create habit event id here
        Intent current_habit_intent = getIntent();
        current_habit_id = current_habit_intent.getStringExtra("current_habit_id");
        today =current_habit_intent.getStringExtra("Today");
        ImageView addImage = findViewById(R.id.addImage);

        addImage.setVisibility(today.equals("today")? ImageView.VISIBLE: ImageView.INVISIBLE );


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
                            }else{
                                Cloud_location = String.valueOf(doc.getData().get("Location"));
                            }
                            if ( doc.get("Comment") == null){
                                Cloud_comment = "No comment provide";
                            }else{
                                Cloud_comment = String.valueOf(doc.getData().get("Comment"));
                            }
                            if ( doc.get("Uri") == null){
                                Cloud_comment = "No photo provide";
                            }else{
                                Cloud_photo = String.valueOf(doc.getData().get("Uri"));
                            }


                            String id = doc.getId();
                            String date = String.valueOf(doc.getData().get("Date"));
                            habitEventDataList.add(new HabitEvent(id,  Cloud_photo, Cloud_location, Cloud_comment, date));



                        }

                            if (habitEventDataList.size()== 0 && today.equals("today") ){
                                    addImage.setVisibility(ImageView.VISIBLE);

                            }else{
                                if(today.equals("today")) {


                                    System.out.println("--------1111-----------2222--------" + habitEventDataList.size());

                                    String valid_until = habitEventDataList.get(habitEventDataList.size() - 1).getDate();
                                    System.out.println("--------1111-----------2222--------" + valid_until);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String valid_now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                                    System.out.println("--------1111-----------2222--444------" + valid_now);
                                    Date strDate = null;
                                    Date current_Date = null;

                                    try {

                                        strDate = sdf.parse(valid_until);
                                        current_Date = sdf2.parse(valid_now);
                                        System.out.println("--------1111-----------2222--------" + strDate);
                                        System.out.println("--------1111-----------2222--------" + current_Date);
                                    } catch (Exception pe) {
                                        pe.printStackTrace();
                                    }
                                    System.out.println("--------1111-----------2222--------" + strDate);
                                    System.out.println("--------1111-----------2222--------" + current_Date);
                                    addImage.setVisibility(current_Date.after(strDate) ? ImageView.VISIBLE : ImageView.INVISIBLE);
                                    //addImage.setVisibility(current_Date.after(strDate) ? System.out.println("111") ;: System.out.println("222"););
                                }
                            }



                        habitEventArrayAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Current habit event: " + habitDoc);
                    }
                });
        System.out.println("--------1111--------------------"+habitEventDataList.size());
        //System.out.println("--------1111--------------------"+habitEventDataList.get(-1));



        //-------------
        //habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        //habitEventList.setAdapter(habitEventArrayAdapter);
        //String currentUri =Cloud_photo;
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
                extras.putString("Uri",uri);
                extras.putString("comment", comment);
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

}
