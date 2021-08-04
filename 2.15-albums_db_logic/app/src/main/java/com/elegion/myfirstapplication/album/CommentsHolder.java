package com.elegion.myfirstapplication.album;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.model.Comment;
import com.elegion.myfirstapplication.model.Song;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CommentsHolder extends RecyclerView.ViewHolder{

    private TextView mText;
    private TextView mAuthor;
    private TextView mTimestamp;

    public CommentsHolder(View itemView) {
        super(itemView);
        mText = itemView.findViewById(R.id.comment_text);
        mAuthor = itemView.findViewById(R.id.author);
        mTimestamp = itemView.findViewById(R.id.comment_time);
    }

    public void bind(Comment item) {
        mText.setText(item.getmText());
        mAuthor.setText(item.getmText());
        Date today = new Date();
        long minus = today.getTime() - item.getmTimestamp().getTime();
        if(minus/(1000 * 60 * 60 * 24) >1) {
            String d1  = new SimpleDateFormat("dd-M-yyyy").format(item.getmTimestamp());
            mTimestamp.setText(d1);
        } else {
            String d1 =  new SimpleDateFormat("mm:HH").format(item.getmTimestamp());
        }
    }
}
