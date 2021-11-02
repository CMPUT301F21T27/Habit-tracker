package com.example.team404;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubscribeFragment extends Fragment {

    private Button HabitEventButton;
    public SubscribeFragment(){
        // require a empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_subscribe, container, false);

        HabitEventButton = (Button) root.findViewById(R.id.habit_event_button);
        HabitEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HabitEventActivity.class);
            startActivity(intent);
        });


        return root;
    }
}
