package com.example.recicle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.*;
import com.squareup.picasso.*;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;

import java.text.BreakIterator;
import java.util.List;

public class AndroidVersionsAdapter extends RecyclerView.Adapter<AndroidVersionsAdapter.AndroidHolder> {
    interface onAndroidClickListener{
        void onAndroidClick(AndroidVersions androidVersions, int position);
    }
    private final List<AndroidVersions> versions;
    private final onAndroidClickListener onClickListener;
    public AndroidVersionsAdapter(List<AndroidVersions> versions, onAndroidClickListener onClickListener) {
        this.versions = versions;
        this.onClickListener=onClickListener;
    }
    static class AndroidHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;

        public AndroidHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            //name.setOnClickListener(this);
        }
    }
    @NonNull
    @Override
    public AndroidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        return new AndroidHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroidHolder holder, final int position) {
        final AndroidVersions androidVersion = versions.get(position);
        Picasso.get().load(androidVersion.getImage()).into(holder.image);
        holder.name.setText(androidVersion.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onAndroidClick(androidVersion, position);
                }
            });
        }


    @Override
    public int getItemCount() {
        return versions.size();
    }


}
