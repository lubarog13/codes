package com.elegion.myfirstapplication.album;

import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.model.Comment;
import com.elegion.myfirstapplication.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsHolder> {

    @NonNull
    private final List<Song> mSongs = new ArrayList<>();

    @Override
    public SongsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_song, parent, false);
        return new SongsHolder(view);
    }

    @Override
    public void onBindViewHolder(SongsHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void addData(List<Song> data, boolean isRefreshed) {
        if (isRefreshed) {
            mSongs.clear();
        }
        Collections.sort(data, new Comparator<Song>() {
            @Override
            public int compare(Song song, Song t1) {
                return t1.getId() > song.getId() ? -1 : (t1.getId() < song.getId()) ? 1 : 0;
            }
        });
            Log.d("data", Integer.toString(data.get(0).getId()) + " " + Integer.toString(data.get(1).getId()));
        mSongs.addAll(data);
        notifyDataSetChanged();
    }
}
