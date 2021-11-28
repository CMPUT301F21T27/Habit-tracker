package com.example.team404;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCommentFragment extends DialogFragment {
    //--------------------------------
    //Display fragment to add comment
    //--------------------------------
    private EditText commentName;
    private onFragmentInteractionListener listener;



    public interface onFragmentInteractionListener{
        void onOkPressed(String newComment);

        void onCancelPressed();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentInteractionListener){
            listener = (onFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +"must implement onFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_comment_fragment_layout, null);
        commentName = view.findViewById((R.id.comment_editText));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a nice comment")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String comment = commentName.getText().toString();
                        if (comment.length()==0){
                            Toast.makeText(getActivity(), "Please input valid words", Toast.LENGTH_LONG).show();
                            listener.onCancelPressed();
                        }else{
                            listener.onOkPressed(comment);
                        }


                    }
                }).create();



    }
}
