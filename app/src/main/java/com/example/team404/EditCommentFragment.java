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

public class EditCommentFragment extends DialogFragment {
    private EditText commentName;
    private onFragmentInteractionListener listener;
    Toast toast;



    public interface onFragmentInteractionListener{
        void onEditOkPressed(String newComment);

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
        Bundle bundle = getArguments();
        String c = ( String) bundle.getSerializable("comment");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_comment_fragment_layout, null);
        //account_photoName = view.findViewById(R.id.account_photo_name_editText);
        commentName = view.findViewById((R.id.comment_editText));
        commentName.setText(c);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit my own comment")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //String photo = account_photoName.getText().toString();
                        String comment = commentName.getText().toString();
                        if (comment.length()==0){
                            Toast.makeText(getActivity(), "Please input valid words", Toast.LENGTH_LONG).show();
                            listener.onCancelPressed();
                        }else{
                            listener.onEditOkPressed(comment);
                        }


                    }
                }).create();



    }
    static  EditCommentFragment newInstance (String comment) {
        Bundle args = new Bundle();
        args.putSerializable("comment", comment);
        EditCommentFragment fragment = new EditCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
