package com.example.team404;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HabitEventListActivity extends AppCompatActivity {
    ListView habitEventList;
    ArrayAdapter<HabitEvent> habitEventArrayAdapter;
    ArrayList<HabitEvent> habitEventDataList;
    private ImageView backImage;
    private ImageView addImage;
    private int position ;
    //--------------------------------------------------------------------//
    //I will do later after add firesbase database.
    // Once I get habit id and I will create habit event Id, I can pass all varable to the another activty after firebase
    // --------------------------------------------------------------------//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_list);
        habitEventList=(ListView) findViewById(R.id.habit_event_list);
        habitEventDataList=new ArrayList<>();
        //HabitEvent habitEvent1 = new HabitEvent("Uof A" ,"This is a good habit", "2021-12-12");
        //HabitEvent habitEvent2 = "2021-12-13";
        //habitEventDataList.add(habitEvent1);
        //habitEventDataList.add(habitEvent2);
        //-----------
        // read habit id and create habit event id here
        //-------------
        habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
        habitEventList.setAdapter(habitEventArrayAdapter);

        habitEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position =i;
                HabitEvent currentHabitevent = habitEventDataList.get(i);
                String location = currentHabitevent.getLocation();
                String comment = currentHabitevent.getComments();
                String date = currentHabitevent.getDate();
                Intent intent = new Intent(HabitEventListActivity.this, HabitEventActivity.class);
                Bundle extras = new Bundle();
                extras.putString("location", location);

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

                new AlertDialog.Builder(HabitEventListActivity.this).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                habitEventDataList.remove(selected_item);
                                habitEventArrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No" , null).show();

                return true;
            }
        });
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
                Intent intent = new Intent(HabitEventListActivity.this, AddHabitEventActivity.class);
                Bundle extras = new Bundle();
                //extras.putString("location", phone_);

                //extras.putString("comment", name);
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
            String returnString = data.getStringExtra("editTitle");
            String returnDate = data.getStringExtra("editDate");
            String returnComment = data.getStringExtra("editComment");
            if (returnString.length()!=0 || returnComment.length()!=0){
                HabitEvent newhabitEvent = new HabitEvent(returnString ,returnComment, returnDate);
                habitEventDataList.add(newhabitEvent);
                habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
                habitEventList.setAdapter(habitEventArrayAdapter);
            }else if (returnString.length()==0 && returnComment.length()==0){
                Toast.makeText(this, "please add comment/location", Toast.LENGTH_SHORT).show();
                return;
            }



        }
        if (requestCode == 111) {

            // Get String data from Intent
            String returnString = data.getStringExtra("editTitle");
            String returnDate = data.getStringExtra("editDate");
            String returnComment = data.getStringExtra("editComment");

            if (returnString.length()!=0 || returnComment.length()!=0){
                HabitEvent newhabitEvent = new HabitEvent(returnString ,returnComment, returnDate);
                //habitEventDataList.remove(position);

                habitEventDataList.set(position, newhabitEvent );
                //habitEventArrayAdapter = new HabitEventContent(this,habitEventDataList);
                habitEventList.setAdapter(habitEventArrayAdapter);
            }else if (returnString.length()==0 && returnComment.length()==0){
                Toast.makeText(this, "please add comment/location", Toast.LENGTH_SHORT).show();
                return;
            }



        }
    }
}
