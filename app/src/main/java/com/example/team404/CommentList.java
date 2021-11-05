package com.example.team404;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CommentList extends ArrayAdapter<Comment> {
    private ArrayList<Comment> comments;
    private Context context;


    public CommentList(@NonNull Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.comment_content, parent,false);
        }
        Comment comment = comments.get(position);
        //ImageView account_photo = view.findViewById(R.id.account_photo);
        //TextView account = view.findViewById(R.id.account_text);
        TextView commentName = view.findViewById(R.id.comment_text);
        //.setText(comment.getAccount_photo());
        commentName.setText(comment.getComment());
        return view;
    }
}
