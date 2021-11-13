package com.elegion.myfirstapplication.albums;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elegion.myfirstapplication.R;
import com.elegion.myfirstapplication.model.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Azret Magometov
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsHolder> {

    @NonNull
    private final List<Album> mAlbums = new ArrayList<>();
    private final OnItemClickListener mOnClickListener;

    public AlbumsAdapter(OnItemClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public AlbumsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_album, parent, false);
        return new AlbumsHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumsHolder holder, int position) {
        Album album = mAlbums.get(position);
        holder.bind(album, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void addData(List<Album> data, boolean isRefreshed) {
        if (isRefreshed) {
            mAlbums.clear();
        }

        mAlbums.addAll(data);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Album album);
    }
}
