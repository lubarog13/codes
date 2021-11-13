package com.elegion.myfirstapplication.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.albums.AlbumsActivity;
import com.elegion.myfirstapplication.model.Comment;
import com.elegion.myfirstapplication.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import io.reactivex.annotations.NonNull;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder> {
    @NonNull
    private final List<Comment> mComments = new ArrayList<>();
    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_comment, parent, false);

        return new CommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
    public void addData(List<Comment> data, boolean isRefreshed) {
        if (isRefreshed) {
            mComments.clear();
        }
        Collections.sort(data, new Comparator<Comment>() {
            @Override
            public int compare(Comment song, Comment t1) {
                if(t1.getId()<0 && song.getId()>=0) {
                    return 1;
                } else if(song.getId()<0 && t1.getId()>=0) {
                    return -1;
                }
                return t1.getId() < song.getId() ? -1 : (t1.getId() > song.getId()) ? 1 : 0;
            }
        });
        int val =0;
        for (Comment comment : data) {
            if(comment.getId() <0){
                val = data.indexOf(comment);
                break;
            }
        }
        List<Comment> comments = data.subList(val, data.size());

            mComments.addAll(data);
        notifyDataSetChanged();
    }
}
